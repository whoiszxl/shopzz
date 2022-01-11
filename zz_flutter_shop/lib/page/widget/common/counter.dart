import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

class Counter extends StatefulWidget {
  final int defaultValue;
  final int max;
  final int min;
  Function(int, int) onChanged;

  Counter({Key key, this.defaultValue = 0, this.max, this.min, this.onChanged}): super(key: key);
  @override
  _CounterState createState() => _CounterState();
}

class _CounterState extends State<Counter> {

  final CartPageController _cartPageController = new CartPageController();

  int value;

  @override
  void initState() {
    super.initState();
    value = widget.defaultValue;
  }
  @override
  Widget build(BuildContext context) {
    return LimitedBox(
      maxWidth: 85,
      child: Row(
        children: <Widget>[
          GestureDetector(
            child: const Padding(
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
                  widget.onChanged(value, -1);
                });
              }
            },
          ),
          LimitedBox(
            maxWidth: 45,
            maxHeight: 25,
            child: TextField(
              keyboardType: TextInputType.number,
              maxLines: 1,
              onTap: () {},
              textAlign: TextAlign.center,
              controller: TextEditingController(text: value.toString()),
              style: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.bold,
              ),
              maxLength: 3,
              decoration: const InputDecoration(
                filled: true,
                fillColor: ColorManager.white,
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
            child: const Padding(
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
                  widget.onChanged(value, 1);
                });
              }
            },
          ),
        ],
      ),
    );
  }
}