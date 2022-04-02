import 'package:event_bus/event_bus.dart';

///事件总线
///使用 Application.eventBus.fire(StopPlayEvent()); 发送事件出去
///再通过Application.eventBus.on<StopPlayEvent>().listen((event) { //处理业务逻辑 }) 接收到事件进行处理
class Application{
  static EventBus eventBus = EventBus();
}