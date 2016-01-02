create table batchreceipt (
    id bigint,
    type varchar,
    successCount int,
    failureCount int,
    startTime timestamp,
    endTime timestamp,
    PRIMARY KEY (id));