local res = {}
local IS_SYNC = ARGV[1]
local TICKET_KEY = ARGV[2]
local TICKET_TYPE_USE = ARGV[3]
local TICKET_TYPE_RETURN = ARGV[4]
local TICKET_INFO = ARGV[5]

for k, v in ipairs(KEYS) do
    local updateNum = 1
    local ticket = cjson.decode(v)
    local ticketId = ticket.id
    local isReusable = ticket.isReusable
    local userId = ticket.userId
    local type = ticket.type
    local key = ""
    if isReusable and tonumber(isReusable) == 1 then
        key = TICKET_KEY .. userId .. '_' .. ticketId
    else
        key = TICKET_KEY .. ticketId
    end
    if type and tonumber(type) == tonumber(TICKET_TYPE_USE) then
        local strNowValue = redis.call('get', key)
        local nowValue = nil ~= strNowValue and strNowValue or 0
        if (nowValue + updateNum) > 1 then
            res.code = 1
            res.msg = "失败"
            return cjson.encode(res)
        end
        local incrQuantity = redis.call('incrBy', key, updateNum);
        if incrQuantity > 1 then
            res.code = 1
            res.msg = "失败"
            return cjson.encode(res)
        end
    elseif type and tonumber(type) == tonumber(TICKET_TYPE_RETURN) then
        redis.call('del', key);
        res.code = 0
        res.msg = "成功"
        --return cjson.encode(res)
    else
        res.code = 1
        res.msg = "失败"
        return cjson.encode(res)
    end
    if IS_SYNC and tonumber(IS_SYNC) == 1 then
        redis.call('rpush', TICKET_INFO, cjson.encode(ticket))
        res.code = 0
        res.msg = "成功"
        --return cjson.encode(res)
    end
end
res.code = 0
res.msg = "成功"
return cjson.encode(res)
