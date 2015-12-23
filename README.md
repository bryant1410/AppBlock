#Android 应用程序屏蔽  

------

## 介绍

这是一个用于屏蔽其他应用的应用，设置你想屏蔽的应用程序列表后，当被屏蔽的程序出现在栈顶时候，会弹出一个屏蔽Activity来盖住TA。可以基于当前代码做扩展开发，如 **应用锁** 类。

原理：后台运行一个Service，定期检测当前栈顶的App是否属于被屏蔽列表，如果属于就弹出一个屏蔽Activity。

## License

    Copyright 2015 Cundong

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
