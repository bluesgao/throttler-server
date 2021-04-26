local key = KEYS[1]
local val = ARGV[1]
redis.call('SET', key, 123)

if type(key) == string and string.len(key)>0
then
    redis.call('SET', key, val)
    return 1
else
    return 0
end