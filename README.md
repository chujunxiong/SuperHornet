# SuperHornet
一个综合库，3条链式调用帮你完成在开发中最常做的3件事：异步网络请求，跨组件通信，线程间切换

### 如何使用
##### 异步网络请求： 
```java
 DataRequest.init(context)
                .RequestType(RequestType.JsonArray)
                .Method(Request.Method.GET) 
                .URL(url)
                .Listener(new ResponseListener() { 
                    @Override
                    public void sucess(Object var) {
                      
                    }

                    @Override
                    public void error(Object var) {
                      
                    }
                }).finish(); 
```
说明： init中传入context，RequestType中传入请求类型（例如：jsonArray,jsonObject,String），Method中传入请求方式（POST或者GET），
URL中传入请求的地址，Listener中传入监听器回调对象，最后别忘了调用finish方法，否则没法工作。

##### 跨组件通信：
```java
  Communicate.init(context).Target(XXXX.class).Data(data).Function("XXXX").finish();
```
说明：init中传入context，Target中传入你要通信的那个组件的类的class对象，Data中传入通信数据，Function中传入在被通信组件类中的定义的方法的名字，最后依然是要调用finish方法，否则无法工作。

##### 线程间切换：
```java
        Message msg = new Message();
        HandlerProxy.getDefault().attach(new SwitchCallBack() {
            @Override
            public void UseData(Message msg) {
                tv.setText(msg.obj.toString());
            }
        }).DeliverData(msg);
```
说明： 在需要线程间切换更新UI时，需要先构造一个Message对象,然后使用HandlerPorxy对象的attach方法中传入一个SwitchCallBack回调对象
来完成具体的操作，最后需要调用DeliverData方法来触发线程切换。

##### OneMoreThing：
在进行跨组件通信时，一定要在被通信组件中进行订阅和注册操作，例如，一个service要想和一个activity通信，需要在activity中先通过以下方式进行订阅和注册
```java
  Subscribe.getDefault().register("XXXX",context);
```
说明：在register中传2个参数，一个是用来标识组件类的名字，一个是context


在跨组件通信时需要在被通信组件的类中定义接收通信数据的方法，在定义该方法时还可以使用java注解来标记该方法的运行环境（是在子线程中还是主线程中）
```java
    @Register(ThreadType = ThreadType.MainThread)
    public void getNewMessage(Object var) {}

    @Register(ThreadType = ThreadType.Background)
    public void getMessage(Object var) {}
```
说明：可以使用@Register注解来完成方法标记，只需要指定ThreadType即可，当然如果不指定会默认为在主线程中运行。
