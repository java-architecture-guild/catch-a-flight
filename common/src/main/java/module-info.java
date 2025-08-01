module common {
    requires static lombok;
    requires spring.context;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;

    exports jag.catchflight.common.annotations.domain;
    exports jag.catchflight.common.annotations.events;
    exports jag.catchflight.common.events;
    exports jag.catchflight.common.annotations.hexagonal;
    exports jag.catchflight.common.policy;
    exports jag.catchflight.common.controller;
    exports jag.catchflight.common.persistence;
}
