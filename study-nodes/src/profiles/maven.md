#### super pom
pom文件可继承 → 高效复用\
maven 的 super pom → 默认继承（mvn help:effective-pom 会显示super pom的默认配置）
#### 依赖冲突
简单讲，Maven 2.0 加入新特性 ：传递性依赖（解决了引入一个依赖，该依赖又依赖于一个新的依赖进行工作，maven也会将它引入进来）。\
它导致了 依赖冲突 的问题(两个依赖的基础依赖相同，该引入哪一个)，因此Maven 提供了依赖规则： 最短路径优先 [ 依赖层级少的优先引入 ]  → 第一声明优先 [ 依据被引入的顺序进行优先度计算 ] （这里又有一个问题，两个相同的依赖（version不一样），顺序优先度和版本优先度哪个优先级高？如果我们在pom文件中显式引入两个相同的依赖，下方的依赖版本比上方的版本高，那么在依赖库里可以看到版本高的会覆盖版本低的。\
**出现冲突应该遵守原则**
+ 使用较高版本
+ 使用稳定版本
+ 不要轻易exclude（不能确定 引用依赖时）\
Duplicated classers（类名冲突） → 如果依赖的两个类的完全限定名一致时，需要干掉一个依赖。\
hdoop没了解,暂时如此~
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!--maven 2.0以上要求pom模型版本4.0.0-->
    <modelVersion>4.0.0</modelVersion>

    <!--父子模块的实现，继承父模块的设定，可以覆盖-->
    <parent>
        <groupId>company.common</groupId>
        <artifactId>company-supom-generic</artifactId>
        <version>1.3.9</version>
    </parent>

    <!--
        在父子模块中，此约束省略。
        组织标识一般由三部分组成，第一部分代表背景（比如org一般代表非盈利组织，com一般代表商业背景，不具有绝对性），
        第二部分是组织名，第三部分是工程名。
        同时它定位了项目在maven仓库的存储区域，类似java的包结构。
    -->
    <groupId>com.company.example</groupId>
    <!-- 工程名 -->
    <artifactId>example</artifactId>
    <!--
        打包的格式，默认是jar,允许格式（pom, jar, maven-plugin, ejb, war, ear, rar, par）
        选择pom格式获得的并非实体包，只是一个引用其他Maven项目的pom。
        实现方式是继承自maven工程的 super pom 中的maven-plugin。
        ejb、ear、par有兴趣可以自己查资料了解。
    -->
    <packaging>jar</packaging>
    <!--
        工程版本号，命名及更新规范：
        <主版本>.<次版本>.<增量版本>
        主版本：重大架构变更; 次版本：较大范围的功能增加或删除; 增量版本：日常bug修复或需求发布
        ========================================================================
        <快照版本> 「通过版本号末尾添加 -SNAPSHOT」
        目的：方便开发与联调
        命名规范：比现行版本高一个增量版本；
        注意事项：版本相同时产生覆盖; 现行版本发布上线，比它低级的快照版本全部废弃。
        拉取策略：时间（天）
        ========================================================================
        自动化策略 ： RELEASE -> 自动化更新版本，分为prepare, perform\rollback阶段，需要maven-relaease-plugin的支持。
        进程：
        release:prepare -> 准备版本发布，依次执行下列操作
            1. 检查项目是否有未提交的代码
            2. 检查项目是否有快照版本依赖
            3. 根据用户的输入将快照版本升级为发布版
            4. 将POM中的SCM信息更新为标签地址
            5. 基于修改后的POM执行maven构建
            6. 提交POM变更
            7. 基于用户输入为代码打标签
            8. 将代码从发布版升级为新的快照版
            9.提交POM变更
        release: rollback
            回退release: prepare所执行的操作。将POM回退至release:prepare之前的状态，并提交。
            注意：该步骤不会删除release:prepare生成的标签，需要用户手动删除
        release: perform -> 执行版本发布
            签出release:prepare生成的标签中的源代码，并在此基础上执行mvn deploy命令打包并部署构件至仓库。
        使用本插件需要正确的版本控制信息。
        同时，本插件支持快照版本的自动化创建，步骤与prepare形似。
    -->
    <version>1.0.0</version>

    <!-- 工程描述 -->
    <description>example</description>

    <!--
        自定义属性，一般用于父子项目统一的版本管理。
        实现方式： <自定义标签名>value</自定义标签名>，在依赖的版本标签中填入${自定义标签名}，maven自动置换
        <project.build.sourceEncoding>、<project.reporting.outputEncoding>、<java.version>属于spring-boot附带，实现项目构建、日志
        的编码选择以及java版本的控制。
    -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>

        <spring.boot.version>1.5.9.RELEASE</spring.boot.version>
    </properties>


    <!--顾名思义 => 模块组，结合<parent>实现父子项目-->
    <modules>
        <module>example.child</module>
    </modules>

    <!--
        依赖管理，一般用于父子项目。
        特性：子pom没有在该标签注册的依赖maven拒绝引入，同时注册在该标签下的依赖需要在新的<dependencies>标签中注册才会被引入。
    -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <!--
                    特殊标识
                    RELEASE : 自动选择仓库中最新的版本
                -->
                <version>${spring.boot.version}</version>
                <!-- 过滤传递性依赖，请确定使用前后置 -->
                <exclusions>
                </exclusions>
                <!-- 依赖引入类型，一一对应<packaging>对应的打包类型，默认为jar -->
                <type>pom</type>
                <!--
                    依赖过滤规则，过滤项目打包时不需要的依赖，默认为compile。
                    允许过滤规则： compile, runtime, provided, test, system, import =>
                        ompile（default）： 所有阶段都适用，会随项目一起发布；
                        runtime ： 只在运行时生效，例如jdbc ，排除编译时期的影响；
                        provided：表明dependency 由JDK或者容器提供，只在测试和编译时生效；
                        test：只在测试时生效；
                        system：类似provided，但是需要以外部jar包的形式提供，maven不会在仓库（本地&远程）查找；
                        import：用于导入其他pom中的dependencyManagement元素；
                -->
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <!--注册项目依赖，使用在父子项目中是配置全局通用依赖-->
    <dependencies>
    </dependencies>

    <build>
        <!-- build命令(mvn)的参数，默认为install，即 mvn == mvn install -->
        <defaultGoal>install</defaultGoal>
        <!-- build目标的存储区域，默认项目/target 下 -->
        <directory>${basedir}/target</directory>
        <!-- build目标的名字，默认项目名-版本 -->
        <finalName>${artifactId}-${version}</finalName>
        <!--
            定义*.properties文件，包含一个properties列表，该列表会应用的支持filter的resources中。
            也就是说，定义在filter的文件中的"name=value"值对会在build时代替${name}值应用到resources中。
            Maven的默认filter文件夹是${basedir}/src/main/filters/。
        -->
        <filters>
            <filter>${basedir}/src/main/filters/</filter>
        </filters>

        <!--
            资源文件配置：
            targetPath：指定build后的resource存放的文件夹。该路径默认是basedir。通常被打包在JAR中的resources的目标路径为META-INF。
            filtering：true/false，表示为这个resource，filter是否激活。
            directory：定义resource所在的文件夹，默认为${basedir}/src/main/resources。
            includes：指定作为resource的文件的匹配模式，用*作为通配符。
            excludes：指定哪些文件被忽略，如果一个文件同时符合includes和excludes，则excludes生效。
            testResources与Resources类似，只在测试阶段生效。不被部署。
        -->
        <resources>
            <resource>
                <targetPath>META-INF</targetPath>
                <filtering>false</filtering>
                <directory>${basedir}/src/main/plexus</directory>
                <includes>
                    <include>configuration.xml</include>
                </includes>
                <excludes>
                    <exclude>**/*.properties</exclude>
                </excludes>
            </resource>
        </resources>
        <testResources>
        </testResources>

        <!--
            maven插件，与packing的maven-plugin对应。
            extensions：true/false，是否加载plugin的extensions，默认为false。
            inherited：true/false，这个plugin是否应用到该POM的孩子POM，默认true。
            configuration：配置该plugin期望得到的properies。集成父pom的配置
            classifier: 插件配置
            dependencies + executions + pluginManagement:类似上方相同名称的标签，不过作用于插件
        -->
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.0</version>
                <extensions>false</extensions>
                <inherited>true</inherited>
                <configuration>
                    <classifier>test</classifier>
                </configuration>
                <dependencies>
                </dependencies>
                <executions>
                </executions>
            </plugin>
        </plugins>
        <pluginManagement>
        </pluginManagement>

    </build>

    <!--
        针对不同环境提供不同的配置环境。
        company-supom-genric 定义了四类，即 本地（local）、开发（dev）、测试（beta）、prod（线上）[测试扩展 noah环境 ] ，
        扩展需求 ： beta-press（压力测试，要求数据库环境）；prod-v3（根据不同的请求方，做不同发布，配置不同接口）；
                  beta-mock （提供一些接口的mock ，mock可以使用catcher工具，不需要定义特有配置环境）。
    -->
    <profiles>
        <profile>
            <id>local</id>
            <properties>
                <deploy.type>local</deploy.type>
            </properties>
        </profile>
    </profiles>

</project>
```