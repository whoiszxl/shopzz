import React from 'react'
import { View, FlatList, Platform } from 'react-native'
import FlowListItem from './FlowListItem'
const invariant = require('invariant')

export default class FlowList extends React.PureComponent {

  constructor(props) {
    super(props)
    // this._checkProps(props)
  }

  _listRef = null
  _width = 0
  _headerHeight = 0
  _innerHeight = 0
  _dataSource = []
  _itemHeights = []
  _allRendered = false
  _itemDidUpdates = []
  _itemRefs = []
  _updateTimeout = null

  _checkProps(props) {
    const { itemHeight } = props
    invariant(itemHeight, 'itemHeight is required')
    invariant(typeof itemHeight === 'function', 'itemHeight must be a function')
  }

  _renderHeader() {
    const { ListHeaderComponent } = this.props
    if (!ListHeaderComponent) {
      return null
    }
    const header = typeof ListHeaderComponent === 'function' ? ListHeaderComponent() : ListHeaderComponent
    return (
      <View
        onLayout={({ nativeEvent }) => {
          const { height } = nativeEvent.layout
          if (this._headerHeight != height) {
            this._headerHeight = height
          }
        }}
      >
        {header}
      </View>
    )
  }

  _computePositions = (byRender) => {
    if (this._isForce) {
      this._isForce = false
      return
    }
    const {
      data = [],
      numColumns = 2,
    } = this.props

    const columnHeights = []
    const originItems = []
    data.forEach((item, index) => {
      let columnIndex = 0
      for (let idx = 1; idx < numColumns; idx++) {
        if ((columnHeights[idx] || 0) < (columnHeights[columnIndex] || 0)) {
          columnIndex = idx
          break
        }
      }
      const offsetTop = columnHeights[columnIndex] || 0
      const height = this._itemHeights[index]
      columnHeights[columnIndex] = offsetTop + (height || 0)
      const obj = {
        height,
        columnIndex,
        offsetTop,
        item,
        index,
      }
      originItems.push(obj)
    })
    const containerHeight = columnHeights.sort((a, b) => b - a)[0] || 0

    const dataSource = []
    let rowIndex = 0
    let rowData = []
    let rowOffsetTop = 0
    originItems.forEach((data, dataIndex) => {
      if (rowData.length == numColumns) {
        rowOffsetTop += dataSource[rowIndex].height || 0
        rowData = []
        rowIndex++
      }
      rowData.push(data)
      const bottomItem = rowData.sort((a, b) => ((b.height || 0) + b.offsetTop) - ((a.height || 0) + a.offsetTop))[0]
      const height = bottomItem.offsetTop - rowOffsetTop + (bottomItem.height || 0)
      dataSource[rowIndex] = {
        rowIndex,
        rowData,
        height,
        rowOffsetTop
      }
      if (dataIndex == data.length - 1) {
        dataSource[rowIndex].height = containerHeight - rowOffsetTop
      }
    })
    this._dataSource = dataSource
    if (!byRender) {
      if (!this._allRendered) {
        this._allRendered = true
      }
      this._isForce = true
      this.forceUpdate()
    }
  }

  _updateIfNeeded = () => {
    const { numColumns = 2 } = this.props
    const itemDidUpdates = JSON.parse(JSON.stringify(this._itemDidUpdates))
    const itemHeights = JSON.parse(JSON.stringify(this._itemHeights))
    if (!itemHeights.some(o => o === null) && !itemDidUpdates.some((o, index) => (o === null && this._itemRefs[Math.floor(index / numColumns)])) && itemDidUpdates.some(o => o === 2)) {
      clearTimeout(this._updateTimeout)
      this._updateTimeout = setTimeout(() => {
        this.forceUpdate()
      }, 10)
    }
  }

  scrollToEnd = ({ animated = true } = {}) => {
    this.scrollToOffset({
      animated,
      offset: this._innerHeight,
    })
  }

  scrollToIndex = ({
    animated = true,
    index = 0,
    viewOffset = 0,
    viewPosition = 0
  }) => {
    const { numColumns = 2 } = this.props
    const rowIndex = Math.floor(index / numColumns)
    const item = this._dataSource[rowIndex].rowData[index % numColumns]
    let offset = item.offsetTop + this._headerHeight
    if (viewOffset) {
      offset += viewOffset
    } else if (viewPosition) {
      offset += viewPosition * item.height
    }
    this.scrollToOffset({
      animated,
      offset,
    })
  }

  scrollToOffset = ({ animated, offset }) => this._listRef.scrollToOffset({ animated, offset })

  recordInteraction = () => this._listRef.recordInteraction()

  flashScrollIndicators = () => this._listRef.flashScrollIndicators()

  getScrollResponder = () => this._listRef.getScrollResponder()

  getNativeScrollRef = () => this._listRef.getNativeScrollRef()

  getScrollableNode = () => this._listRef.getScrollableNode()

  setNativeProps = () => this._listRef.setNativeProps()

  render() {
    const {
      renderItem,
      numColumns = 2,
      style,
      onContentSizeChange,
      ListFooterComponent,
      data,
      contentContainerStyle,
      onEndReachedThreshold = 0.2,
      onEndReached
    } = this.props

    const columnWidth = this._width / numColumns
    
    this._itemHeights.length = data.length

    this._itemDidUpdates = []
    this._itemDidUpdates.length = data.length

    this._computePositions(true)

    return (
      <FlatList
        {...this.props}
        ref={ref => this._listRef = ref}
        style={[{ flex: 1 }, style]}
        removeClippedSubviews={Platform.OS === 'android'}
        onEndReachedThreshold={onEndReachedThreshold}
        onEndReached={info => {
          if (!JSON.parse(JSON.stringify(this._itemHeights)).some(o => o === null)) {
            onEndReached && onEndReached(info)
          }
        }}
        numColumns={1}
        data={this._dataSource}
        keyExtractor={(item, index) => `row_${index}`}
        contentContainerStyle={{ minHeight: '100%', ...(contentContainerStyle || {}) }}
        ListHeaderComponent={this._renderHeader()}
        ListFooterComponent={this._allRendered ? ListFooterComponent : null}
        getItemLayout={(data, index) => ({ 
          length: this._dataSource[index].height || 0, 
          offset: this._dataSource[index].rowOffsetTop || 0, 
          index 
        })}
        onContentSizeChange={(w, h) => {
          this._innerHeight = h
          if (this._width != w) {
            this._width = w
            this.forceUpdate()
          }
          onContentSizeChange && onContentSizeChange(w, h)
        }}
        renderItem={({ item, index }) => {
          return (
            <FlowListItem
              ref={ref => {
                this._itemRefs[index] = ref
              }}
              rowIndex={index}
              rowItem={item}
              renderItem={renderItem}
              columnWidth={columnWidth}
              onItemDidUpdate={(flag) => {
                const { rowData } = item
                const itemDidUpdates = JSON.parse(JSON.stringify(this._itemDidUpdates))
                rowData.forEach((data) => {
                  const { index } = data
                  if (itemDidUpdates[index] === null) {
                    this._itemDidUpdates[index] = flag
                  }
                })
                this._updateIfNeeded()
              }}
              onItemHeightChange={(height, index) => {
                const preHeight = this._itemHeights[index]
                if (this._itemHeights[index] !== height) {
                  const preAllLoaded = !JSON.parse(JSON.stringify(this._itemHeights)).some(o => o === null)
                  this._itemHeights[index] = height
                  const allLoaded = !JSON.parse(JSON.stringify(this._itemHeights)).some(o => o === null)
                  if (allLoaded) {
                    if (preAllLoaded) {
                      this._itemDidUpdates[index] = 2
                      if (preHeight && height && Math.abs(preHeight - height) >= 0.01) {
                        this._updateIfNeeded()
                      }
                    } else {
                      this._computePositions()
                    }
                  }
                }
              }}
            />
          )
        }}
      />
    )
  }
}