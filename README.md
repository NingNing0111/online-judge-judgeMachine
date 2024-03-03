# 介绍

&emsp;基于[online-judge-sandbox](https://github.com/NingNing0111/online-judge-sandbox)代码沙箱实现的代码判题机。

## 业务流程

- 获取测试用例的输入文件，封装ExecuteCodeRequest对象；
- 调用代码沙箱API，获取ExecuteCodeResponse对象；
- 根据ExecuteCodeResponse结果，判断代码是否超时、内存溢出等。
- 获取测试用例的输出文件，比对ExecuteCodeResponse运行结果；
- 封装结果，返回