# Simple java app demo (Postgres) using GraalVM's Native Image Generation

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
./target/native-java-postgres-demo
```
