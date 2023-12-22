INSERT
INTO user_entity(
    username,
    password,
    email,
    expired,
    locked,
    credential_expired,
    enabled,
    role)
VALUES(
    'forDeletion',
    'password',
    'fordeletion@gmail.com',
    88888888888888,
    false,
    88888888888888,
    true,
    0
)
ON CONFLICT DO NOTHING
;