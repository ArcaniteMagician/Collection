# Collection
开发中常见的代码集合，会尽量降低耦合程度。

项目设计及想要实现的内容，想法、过程记录：

-使用MVP模式，主要参考http://www.jcodecraeer.com/a/anzhuokaifa/2017/1020/8625.html?1508484926
-使用通用的RecylerView适配器
-以ApplicationContext创建一个单例Dialog，将Dialog逻辑放在BaseActivity和BaseFragement中统一管理
-模仿水杯的TabHost方式
-对于不同类型功能的界面，有不同的BaseActivity
-我开始喜欢上ButterKnife了（20180730，发现要使用ButterKinfe，需要做好几步配置，我想这个项目尽量减少配置类的工作，所以放弃了）
-RxJava 2.0和Retrofit的练习，做一个登录界面
