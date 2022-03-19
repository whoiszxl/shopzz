import 'package:flutter/material.dart';

class Counter extends StatefulWidget {
  // 默认值
  final int defaultValue;
  // 最大值
  final int max;
  // 最小值
  final int min;
  Counter({Key key, this.defaultValue = 0, this.max, this.min }): super(key: key);
  @override
  _CounterState createState() => _CounterState();
}

class _CounterState extends State<Counter> {
  int value;
  @override
  void initState() {
    super.initState();
    value = widget.defaultValue;
  }
  @override
  Widget build(BuildContext context) {
    return LimitedBox(
      maxWidth: 20,
      child: Row(
        children: <Widget>[
          GestureDetector(
            child: Padding(
              padding: EdgeInsets.fromLTRB(0, 0, 5, 0),
              child: Icon(
                Icons.remove,
                size: 15,
              ),
            ),
            onTap: () {
              if (widget.min == null || value > widget.min) {
                setState(() {
                  value--;
                });
              }
            },
          ),
          LimitedBox(
            maxWidth: 40,
            maxHeight: 20,
            child: TextField(
              keyboardType: TextInputType.number,
              maxLines: 1,
              onTap: () {},
              textAlign: TextAlign.center,
              controller: TextEditingController(text: value.toString()),
              style: TextStyle(
                fontSize: 10,
                fontWeight: FontWeight.bold,
              ),
              maxLength: 2,
              decoration: InputDecoration(
                filled: true,
                fillColor: Colors.grey,
                border: InputBorder.none,
                counterText: "",
              ),
              onChanged: (text) {
                var inputValue = int.parse(text);
                if (widget.min != null && inputValue < widget.min) {
                  setState(() {
                    value = value;
                  });
                  return;
                }
                if (widget.max != null && inputValue > widget.max) {
                  setState(() {
                    value = value;
                  });
                  return;
                }
                value = inputValue;
              },
            ),
          ),
          GestureDetector(
            child: Padding(
              padding: EdgeInsets.fromLTRB(5, 0, 0, 0),
              child: Icon(
                Icons.add,
                size: 15,
              ),
            ),
            onTap: () {
              if (widget.max == null || value < widget.max) {
                setState(() {
                  value++;
                });
              }
            },
          ),
        ],
      ),
    );
  }
}