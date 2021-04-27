local key = KEYS[1] --限流KEY
local limit = tonumber(ARGV[1]) --限流大小
local window = tonumber(ARGV[2]) -- 时间窗
local ok = redis.call('set',key,0,'ex', window, 'nx') -- 初始化key
local current = tonumber(redis.call('get', key) or 0)
if current + 1 > limit then --如果超出限流大小
    return 0
else
    redis.call("INCRBY", key,"1") --请求数+1
    -- redis.call("expire", key,"2") --设置2秒过期
    return 1
end