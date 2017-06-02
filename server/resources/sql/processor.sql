-- name: processor-list-query
-- Gets list of all active processors
SELECT * FROM signal.processors WHERE deleted_at IS NULL

-- name: find-by-id-query
-- gets a processor by its uuid/primary key
SELECT * FROM signal.processors WHERE deleted_at IS NULL AND id = :id

-- name: insert-processor<!
-- inserts a new processor definition
INSERT INTO signal.processors
(name,description,repeated,persistent,filters,
reducers,predicates,output,input,created_at,updated_at)
VALUES
(:name,
:description,
:repeated,
:persistent,
:filters::json,
:reducers::json,
:predicates::json,
:output::json,
:input::json,
NOW(),NOW());

-- name: update-processor<!
-- updates definition and recipients
UPDATE signal.processors SET
name = :name,
description = :description,
repeated = :repeated,
persistent = :persistent,
input = :input::json,
filters = :filters::json,
reducers = :reducers::json,
predicates = :predicates::json,
output = :output::json,
updated_at = NOW()
WHERE id = :id

-- name: delete-processor!
-- disables a processor
UPDATE signal.processors SET deleted_at = NOW() WHERE id = :id