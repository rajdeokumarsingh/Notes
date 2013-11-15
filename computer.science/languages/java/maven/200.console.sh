
mvn clean
    # 删除target目录。maven构建的文件(class, jar)都放在target目录

mvn compile
    # 编译项目主代码

mvn test
    # 测试

mvn package
    # 打包

mvn install
    # 将包安装到本地仓库

mvn deploy
    # 将构建部署到私服


mvn help:system    # 输出所有java相关环境变量。


mvn clean [compile|test|package|install]

mvn archetype:generate
    # 创建pom.xml, maven目录结构
    # TODO: 没有网络，出错了


mvn dependency:list
mvn dependency:tree

