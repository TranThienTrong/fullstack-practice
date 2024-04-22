create table customer (
                          id SERIAL,
                          name varchar(255) not null,
                          email varchar(255) not null,
                          age integer not null,
                          primary key (id)
)