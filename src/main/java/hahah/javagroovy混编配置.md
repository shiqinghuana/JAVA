###maven配置
 
     idea自带maven 2 /3 
     配置文件 setting.xml 里添加本地仓库路径
     
###pom.xml添加以下依赖后更新
    <dependencies>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>2.4.6</version>
        </dependency>


    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.3</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <compilerId>groovy-eclipse-compiler</compilerId>
                    <verbose>true</verbose>
                    <fork>true</fork>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.codehaus.groovy</groupId>
                        <artifactId>groovy-eclipse-compiler</artifactId>
                        <version>2.9.2-01</version>
                    </dependency>  <!-- for 2.8.0-01 and later you must have an explicit dependency on groovy-eclipse-batch -->
                    <dependency>
                        <groupId>org.codehaus.groovy</groupId>
                        <artifactId>groovy-eclipse-batch</artifactId>
                        <version>2.4.3-01</version>
                    </dependency>
                </dependencies>
            </plugin>
            <plugin>
                <groupId>org.codehaus.groovy</groupId>
                <artifactId>groovy-eclipse-compiler</artifactId>
                <version>2.9.2-01</version>
                <extensions>true</extensions>
            </plugin>
        </plugins>
    </build>
    
    
### 跟新依赖
