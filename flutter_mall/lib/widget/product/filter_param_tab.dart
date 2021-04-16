import 'package:flutter/material.dart';
import 'package:flutter_mall/util/color.dart';

///过滤器tab
class FilterParamTab extends StatefulWidget {
  FilterParamTab({
    @required this.onTabChangedListener,
    @required this.onTabStyleChangedListener,
    this.clickItemPositionCallback,
    this.initialSelection = 0,
    this.showTag = 0,
  }) : assert(onTabChangedListener != null);
  final Function(int position) onTabChangedListener;
  final Function(int position) onTabStyleChangedListener;
  final ValueChanged<int> clickItemPositionCallback;
  int initialSelection;
  int showTag;

  @override
  _FilterParamTabState createState() {
    return _FilterParamTabState();
  }
}

class _FilterParamTabState extends State<FilterParamTab> {
  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        GestureDetector(
          onTap: () {
            if(widget.initialSelection == 0) return;
            _setTab(0);
          },
          child: Text('综合', style: TextStyle(fontSize: 14, color: widget.initialSelection == 0 ? primary : Colors.black87)),
        ),
        GestureDetector(
          onTap: () {
            if(widget.initialSelection == 1) {
              return;
            }
            _setTab(1);
          },
          child: Text('销量', style: TextStyle(fontSize: 14, color: widget.initialSelection == 1 ? primary : Colors.black87),
          ),
        ),
        GestureDetector(
          onTap: () {
            _setTab(widget.initialSelection == 2 ? 3 : 2);
          },
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text('价格', style: TextStyle(fontSize: 14,
                  color: (widget.initialSelection == 2 || widget.initialSelection == 3) ? primary : Colors.black87,
                ),
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  //如果是2和3选主色
                  Icon(Icons.arrow_drop_up, size: 10, color: widget.initialSelection == 2 ? primary : Colors.black87),
                  Icon(Icons.arrow_drop_down, size: 10, color: widget.initialSelection == 3 ? primary : Colors.black87),
                ],
              ),
            ],
          ),
        ),


        GestureDetector(
          onTap: () {
            if(widget.initialSelection == 4) return;
            _setTab(4);
          },
          child: Text('筛选', style: TextStyle(fontSize: 14, color: widget.initialSelection == 4 ? primary : Colors.black87),
          ),
        ),
        
        GestureDetector(
          onTap: () {
            if(widget.showTag == 0){
              setState(() {
                widget.showTag = 1;
              });
              widget.onTabStyleChangedListener(1);
            }else{
              setState(() {
                widget.showTag = 0;
              });
              widget.onTabStyleChangedListener(0);
            }
          },
          child: Icon(
              widget.showTag == 0 ? Icons.auto_awesome_mosaic : Icons.reorder,
              size: 18,
              color: Colors.black54
          ),
        ),
      ],
    );
  }

  void _setTab(int position) {
    widget.clickItemPositionCallback(position);
    widget.onTabChangedListener(position);
    setState(() {
      widget.initialSelection = position;
    });
  }
}