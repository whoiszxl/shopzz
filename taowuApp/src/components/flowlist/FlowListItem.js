import React from 'react'
import { View } from 'react-native'

export default class FlowListItem extends React.Component {

  constructor(props) {
    super(props)

    this.state = {
      opacity: 0
    }
  }

  shouldComponentUpdate(nextProps, nextState) {
    if (JSON.stringify(this.props) != JSON.stringify(nextProps)) {
      return true
    }
    const { onItemDidUpdate } = this.props
    onItemDidUpdate(0)
    return false
  }

  componentDidUpdate() {
    const { onItemDidUpdate } = this.props
    onItemDidUpdate(1)
  }

  render() {
    const {
      rowIndex,
      rowItem,
      renderItem,
      columnWidth,
      onItemHeightChange
    } = this.props

    const { rowData, rowOffsetTop, height } = rowItem

    const extraStyle = {}
    if (height > 0) {
      extraStyle.height = height
    }
    if (!rowData.some(item => item.height === undefined)) {
      extraStyle.opacity = 1
      this.state.opacity = 1
    }

    return (
      <View style={{ flexDirection: 'row', opacity: this.state.opacity,  ...extraStyle }} key={`row_${rowIndex}`}>
        {
          rowData.map((data, dataIndex) => {
            const { item, index, columnIndex, offsetTop } = data
            return (
              <View
                key={`row_${rowIndex}_item_${dataIndex}`}
                style={{
                  position: 'absolute',
                  top: offsetTop - rowOffsetTop,
                  left: columnIndex * columnWidth,
                  width: columnWidth,
                }}
                onLayout={e => {
                  onItemHeightChange(e.nativeEvent.layout.height, index)
                }}
              >
                {renderItem && renderItem({ item, index, columnIndex })}
              </View>
            )
          })
        }
      </View>
    )
  }
}