module common {
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;

    exports jag.common.annotations.domain;
    exports jag.common.annotations.events;
    exports jag.common.events;
    exports jag.common.annotations.hexagonal;
    exports jag.common.policy;
    exports jag.common.controller;
    exports jag.common.persistence;
}
