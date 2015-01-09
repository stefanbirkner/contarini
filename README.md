# Contarini 

[![Build Status](https://travis-ci.org/stefanbirkner/contarini.svg?branch=master)](https://travis-ci.org/stefanbirkner/contarini)

Contarini provides a Java model and renderer for search engine meta tags
(e.g. alternative, canonical, description, keywords, robots). The
extension [Contarini JSP](https://github.com/stefanbirkner/contarini-jsp)
provides support for rendering the model with a JSP tag.


## Installation

Contarini is available from [Maven Central](http://search.maven.org/).

    <dependency>
      <groupId>com.github.stefanbirkner</groupId>
      <artifactId>contarini</artifactId>
      <version>0.7.0</version>
    </dependency>


## Usage

Contarini's documentation is stored in the `gh-pages` branch and is
available online at
http://stefanbirkner.github.io/contarini/index.html


## Contributing

You have three options if you have a feature request, found a bug or
simply have a question about Contarini.

* [Write an issue.](https://github.com/stefanbirkner/contarini/issues/new)
* Create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))
* [Write a mail to mail@stefan-birkner.de](mailto:mail@stefan-birkner.de)


## Development Guide

Contarini is build with [Maven](http://maven.apache.org/). If you want
to contribute code than

* Please write a test for your change.
* Ensure that you didn't break the build by running `mvn test`.
* Fork the repo and create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))

The basic coding style is described in the
[EditorConfig](http://editorconfig.org/) file `.editorconfig`.

Contarini supports [Travis CI](https://travis-ci.org/) for continuous
integration. Your pull request will be automatically build by Travis
CI.


## Release Guide

* Select a new version according to the
  [Semantic Versioning 2.0.0 Standard](http://semver.org/).
* Set the new version in the `Installation` section of this readme.
* `mvn release:prepare`
* `mvn release:perform`
* Create release notes on GitHub.
