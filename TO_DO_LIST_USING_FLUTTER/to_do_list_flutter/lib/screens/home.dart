import 'package:flutter/material.dart';
import 'package:to_do_list_flutter/constants/colors.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: tdBGColor,
        elevation: 0,
        title:
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Icon(
            Icons.menu,
            color: TdBlack,
            size: 30,
          ),
          Container(
              height: 40,
              width: 40,
              child: ClipRRect(
                  borderRadius: BorderRadius.circular(20),
                  child: Image.asset(
                      '/assets/images/linkedin profile picture enhanced j.jpg')))
        ]),
      ),
      body: Container(
        child: Text('This is home screen'),
      ),
    );
  }
}
