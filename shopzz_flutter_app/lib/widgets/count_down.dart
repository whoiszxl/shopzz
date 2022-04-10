import 'dart:async';

import 'package:flutter/material.dart';

class Countdown extends StatefulWidget {
  final int timeLeft;
  final DateTime endTime;
  final double height;
  const Countdown({
    Key key,
    this.timeLeft = 0,
    this.endTime,
    this.height = 14,
  }) : super(key: key);
  @override
  _CountdownState createState() => _CountdownState();
}

class _CountdownState extends State<Countdown> {
  var time;
  Timer countdownTimer;

  @override
  void initState() {
    super.initState();
    time = widget.timeLeft;
    initTimer();
  }

  initTimer() {
    countdownTimer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (time > 0) {
        setState(() {
          time--;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    var hour = (time ~/ 3600).toString();
    var minute = ((time % 3600) ~/ 60).toString();
    var second = (time % 60).toString();
    return Container(
      height: widget.height,
      alignment: Alignment.center,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Container(
            width: 20,
            alignment: Alignment.center,
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.black26,
                width: 1,
              ),
              borderRadius: BorderRadius.circular(2),
            ),
            child: Text(
              hour.length == 2 ? hour : "0$hour",
              style: const TextStyle(
                color: Colors.black,
                fontSize: 10,
                fontWeight: FontWeight.bold,
                height: 1,
              ),
            ),
          ),
          const Text(
            ":",
            style: TextStyle(height: 1, fontSize: 12),
          ),
          Container(
            width: 20,
            alignment: Alignment.center,
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.black26,
                width: 1,
              ),
              borderRadius: BorderRadius.circular(2),
            ),
            child: Text(
              minute.length == 2 ? minute : "0$minute",
              style: const TextStyle(
                color: Colors.black,
                fontSize: 10,
                fontWeight: FontWeight.bold,
                height: 1,
              ),
            ),
          ),
          const Text(
            ":",
            style: TextStyle(height: 1, fontSize: 12),
          ),
          Container(
            width: 20,
            alignment: Alignment.center,
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.black26,
                width: 1,
              ),
              borderRadius: BorderRadius.circular(2),
            ),
            child: Text(
              second.length == 2 ? second : "0$second",
              style: const TextStyle(
                color: Colors.black,
                fontSize: 10,
                fontWeight: FontWeight.bold,
                height: 1,
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    countdownTimer.cancel();
  }
}