# Simple web app demo (Micronaut, Apache Cayenne, Postgres) using GraalVM's Native Image Generation

Used frameworks:
* Micronaut [https://micronaut.io/](https://micronaut.io/)
* Apache Cayenne [https://cayenne.apache.org/](https://cayenne.apache.org/)

You need Postgres ([https://www.postgresql.org/](https://www.postgresql.org/)) 
on localhost with database named 'demo_nativem', table 'user',
login 'test', password 'test':

```
CREATE TABLE public."user"
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to test;
    
INSERT INTO public."user"(id, name)
VALUES (1, 'name1'), (2, 'name2');
```

To build native image run the following command:

```
mvn package -Pnative
```

then start native image:

```
./target/native-micronaut-cayenne-demo
```

after the program started, open url [http://localhost:8080/hello/user1](http://localhost:8080/hello/user1) 
to see data from database. To stop theprogram press `Ctrl + C`.
