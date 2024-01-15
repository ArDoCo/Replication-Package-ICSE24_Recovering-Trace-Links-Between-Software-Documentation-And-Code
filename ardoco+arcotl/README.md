# ArDoCo Core

The goal of this project is to connect architecture documentation and models while identifying missing or deviating
elements (inconsistencies).
An element can be any representable item of the model, like a component or a relation.
To do so, we first create trace links and then make use of them and other information to identify inconsistencies.

ArDoCo is actively developed by researchers of
the _[Modelling for Continuous Software Engineering (MCSE) group](https://mcse.kastel.kit.edu)_
of _[KASTEL - Institute of Information Security and Dependability](https://kastel.kit.edu)_ at
the [KIT](https://www.kit.edu).

## Documentation

For more information about the setup or the architecture have a look on the [Wiki](https://github.com/ArDoCo/Core/wiki).
The docs are at some points deprecated, the general overview and setup should still hold.
You can find the generated JavaDocs at [ArDoCo.github.io/Core-Docs](https://ArDoCo.github.io/Core-Docs/).


## Maven

```xml
<dependencies>
	<dependency>
		<groupId>io.github.ardoco.core</groupId>
		<artifactId>pipeline</artifactId> <!-- or any other subproject -->
		<version>VERSION</version>
	</dependency>
</dependencies>
```

For snapshot releases, make sure to add the following repository
```xml
<repositories>
	<repository>
		<releases>
			<enabled>false</enabled>
		</releases>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
		<id>mavenSnapshot</id>
		<url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
	</repository>
</repositories>
```

## Attribution

The initial version of this project is based on the master thesis [Linking Software Architecture Documentation and Models](https://doi.org/10.5445/IR/1000126194).

## Acknowledgements

This work was supported by funding from the topic Engineering Secure Systems of the Helmholtz Association (HGF) and by
KASTEL Security Research Labs (46.23.01).
