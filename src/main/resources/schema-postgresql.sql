--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------

CREATE OR REPLACE FUNCTION deleteAllByIdsAndNames(ids BIGINT[], usernames VARCHAR[]) RETURNS JSON AS '
DECLARE
	foundIds BIGINT[];

	notFoundIds BIGINT[];
	notFoundUsernames TEXT[];

	successIds BIGINT[];
	successNames TEXT[];

	success JSON;
	fail JSON;

	result JSON;

BEGIN
	--------------------------------------------------
	foundIds := ARRAY(SELECT id FROM user_entity WHERE id = ANY(ids) AND username = ANY(usernames) FOR SHARE);
	--------------------------------------------------
	notFoundIds := ARRAY(WITH ids AS (
  				SELECT UNNEST(ids) AS id
			)
			SELECT ids.id
			FROM user_entity
			FULL JOIN ids ON ids.id = user_entity.id
			WHERE user_entity.id IS NULL
			);
	--------------------------------------------------
	notFoundUsernames := ARRAY(WITH usernames AS (
  				SELECT UNNEST(usernames) AS username
			)
			SELECT usernames.username
			FROM user_entity
			FULL JOIN usernames ON usernames.username = user_entity.username
			WHERE user_entity.username IS NULL
			);
	--------------------------------------------------
	IF ARRAY_LENGTH(notFoundIds, 1) > 0 AND ARRAY_LENGTH(notFoundUsernames, 1) > 0
	THEN
		successIds :=
						ARRAY(
						SELECT UNNEST(ids)
						EXCEPT
						SELECT UNNEST(notFoundIds)
					); -- ids - notFoundIds
		successNames :=

						ARRAY(
						SELECT UNNEST(usernames)
						EXCEPT
						SELECT UNNEST(notFoundUsernames)
					); -- usernames - notFoundUsernames

		success := json_build_object(''ids'', successIds, ''names'', successNames);
		fail := json_build_object(''ids'', notFoundIds, ''names'', notFoundUsernames);
		result := json_build_object(''operation'', ''delete'', ''success'', false, ''success'', success, ''fail'', fail);
	ELSE
		DELETE FROM user_entity WHERE id = ANY(foundIds);
		success := json_build_object(''ids'', ids, ''names'', usernames);
		fail := json_build_object(''ids'', ARRAY[]::BIGINT[], ''names'', ARRAY[]::TEXT[]);
		result := json_build_object(''operation'', ''delete'', ''success'', success, ''fail'', fail);
	END IF;
RETURN  result;
COMMIT;
END;
' LANGUAGE plpgsql;

-- select * from deleteAllByIdsAndNames(ARRAY[1, 2, 3, 4, 5], ARRAY['aaaaa', 'bbbbb', 'notexisting1', 'notexisting2']);

-- select * from deleteAllByIdsAndNames(ARRAY[1, 2], ARRAY['aaaaa', 'bbbbb']) AS json;

--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------