
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ChoiceChipSelect extends StatefulWidget {

  final List<SelectOption> selectOptions;

  List<String> selectedIdList;

  int groupIndex;

  ValueChanged<List<String>> selectedIdListValueChanged;

  final void Function(SelectOption option, bool isSelect) onSelect;

  ChoiceChipSelect({Key key, this.groupIndex, this.selectOptions, this.selectedIdList, this.onSelect, this.selectedIdListValueChanged}): super(key: key);

  @override
  _ChoiceChipSelectState createState() => _ChoiceChipSelectState();

}

class _ChoiceChipSelectState extends State<ChoiceChipSelect> {
  @override
  Widget build(BuildContext context) {
    return Wrap(
      direction: Axis.horizontal,
      alignment: WrapAlignment.start,
      spacing: 10,
      runSpacing: 15,
      runAlignment: WrapAlignment.start,
      children: <Widget>[
        ...widget.selectOptions.map((button) {
          return myChoiceChip(
            text: button?.label,
            isSelected: widget.selectedIdList.contains(button?.id),
            onSelected: (isSelected) {
              setState(() {
                widget.selectedIdList[widget.groupIndex] = button?.id;
              });

              widget.selectedIdListValueChanged(widget.selectedIdList);
              widget?.onSelect(button, isSelected);
            }
          );
        })
      ],
    );
  }


  myChoiceChip({String text, bool isSelected, double width, double height = 26, void Function(bool) onSelected}) {
    return SizedBox(
      height: height,
      child: ChoiceChip(
        padding: EdgeInsets.fromLTRB(5, 0, 5, 0),
        pressElevation: 0,
        selectedColor: Colors.white38,
        selected: isSelected,
        backgroundColor: Colors.white38,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(20),
          side: BorderSide(color: isSelected ? Colors.red : Colors.transparent, width: 1),
        ),
        label: SizedBox(
          width: width,
          child: Text(text, textAlign: TextAlign.center, style: TextStyle(fontSize: 12, color: isSelected ? Colors.red : Colors.black)),
        ),

        onSelected: onSelected,
      )
    );
  }
}


class SelectOption {
  String id;
  String label;
  dynamic value;
  SelectOption({ this.id, this.label, this.value });
}