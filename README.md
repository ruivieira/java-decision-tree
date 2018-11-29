# java-decision-tree

Java library implementing [Decision Trees](https://en.wikipedia.org/wiki/Decision_tree) and [Random Forests](https://en.wikipedia.org/wiki/Random_forest).

## installation

You can use this library as a Maven dependency by adding

```xml
<dependency>
    <groupId>org.ruivieira</groupId>
    <artifactId>decisiontree</artifactId>
    <version>0.0.2</version>
</dependency>
```

to your Maven `dependencies`.
You should then specify the dependency location by using one of the following methods:

### bintray

Add the repository information to your `pom.xml`:

```xml
    <profiles>
        <profile>
            <repositories>
                <repository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-ruivieira-maven</id>
                    <name>bintray</name>
                    <url>https://dl.bintray.com/ruivieira/maven</url>
                </repository>
                <repository>
                    <id>jcenter</id>
                    <url>https://jcenter.bintray.com/</url>
                </repository>
            </repositories>
            <pluginRepositories>
                <pluginRepository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintray-ruivieira-maven</id>
                    <name>bintray-plugins</name>
                    <url>https://dl.bintray.com/ruivieira/maven</url>
                </pluginRepository>
            </pluginRepositories>
            <id>bintray</id>
        </profile>
    </profiles>
```

### locally

Or by downloading this reposity and installing the library locally by running

```text
$ mvn install
```

## notes

* The changelog is [here](CHANGELOG.md);