# RedisMongo
RedisMongo



一、Memcache和Redis区别：

		1.Redis中，并不是所有的数据都一直存储在内存中的，这是和Memcache相比一个最大的区别。
		2.Redis在很多方面具备数据库的特征，或者说就是一个数据库系统，而Memcache只是简单的K/V缓存。
		3.他们的扩展都需要做集群；实现方式：master-slave、Hash。
		4.在100k以上的数据中，Memcache性能要高于Redis。
		5.如果要说内存使用效率，使用简单的key-value存储的话，Memcached的内存利用率更高，而如果Redis采用hash结构来做key-value存储，由于其组合式的压缩，其内存利用率会高于Memcache。当然，这和你的应用场景和数据特性有关。
		6.如果你对数据持久化和数据同步有所要求，那么推荐你选择Redis，因为这两个特性Memcache都不具备。即使你只是希望在升级或者重启系统后缓存数据不会丢失，选择Redis也是明智的。
		7.Redis和Memcache在写入性能上面差别不大，读取性能上面尤其是批量读取性能上面Memcache更强

	1)共同点:
		
		Memcache，Redis 都是内存数据库

	2)区别:
	
		1.Memcache
		
			①Memcache可以利用多核优势，单实例吞吐量极高，可以达到几十万QPS,适用于最大程度扛量
			②只支持简单的key/value数据结构，不像Redis可以支持丰富的数据类型。
			③无法进行持久化，数据不能备份，只能用于缓存使用，且重启后数据全部丢失

		2.Redis

			①支持多种数据结构，如string,list,dict,set,zset,hyperloglog
			②单线程请求，所有命令串行执行，并发情况下不需要考虑数据一致性问题。
			③支持持久化操作，可以进行aof及rdb数据持久化到磁盘，从而进行数据备份或数据恢复等操作，较好的防止数据丢失的手段。
			④支持通过Replication进行数据复制，通过master-slave机制，可以实时进行数据的同步复制，支持多级复制和增量复制.
			⑤支持pub/sub消息订阅机制，可以用来进行消息订阅与通知。
			⑥支持简单的事务需求，但业界使用场景很少，并不成熟
			
二、时间戳

	在redis取得当前时的方法为执行time命令

	127.0.0.1:6382> time
	1) "1495780564"
	2) "894089"

	第一行为以 UNIX 时间戳格式表示已经过去的秒数
	第二行为当前这一秒已经过去的微秒数
			
三、Redis的value支持五种数据类型
		
	1)String（字符串）
	
		1.string 是 redis 最基本的类型，你可以理解成与 Memcached 一模一样的类型，一个 key 对应一个 value。
		2.string 类型是二进制安全的。意思是 redis 的 string 可以包含任何数据。比如jpg图片或者序列化的对象。
		3.string 类型是 Redis 最基本的数据类型，string 类型的值最大能存储 512MB。
		
		常用命令行参数：
			SET key value
			设置指定 key 的值
			GET key
			获取指定 key 的值
			GETRANGE key start end
			返回 key 中字符串值的子字符串（这里的start和end即起始子字符的索引，例如字符串abcde,取1至3，即拿到bcd，索引从0开始计数）
			STRLEN key
			返回 key 所储存的字符串值的长度。（String 的 Length）
			INCR key
			将 key 中储存的数字值增一。(String中存为类似102、88的数字字符串情况)
			DECR key
			将 key 中储存的数字值减一。
			APPEND key value
			如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。	
		
	2)Hash（哈希）
	
		1.Redis hash 是一个键值(key=>value)对集合。
		2.Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。
	
		常用命令行参数：
		
			命令均以首字母H开头
		
			HSET key field value
			将哈希表 key 中的字段 field 的值设为 value 。
			HSETNX key field value
			只有在字段 field 不存在时，设置哈希表字段的值。
			HGET key field
			获取存储在哈希表中指定字段的值。
			HMSET key field1 value1 [field2 value2 ]
			同时将多个 field-value (域-值)对设置到哈希表 key 中。
			HMGET key field1 [field2]
			获取所有给定字段的值
			HKEYS key
			获取所有哈希表中的字段
			HVALS key
			获取哈希表中所有值
			HEXISTS key field
			查看哈希表 key 中，指定的字段是否存在。
			HLEN key
			获取哈希表中字段的数量
			HDEL key field1 [field2]
			删除一个或多个哈希表字段
	
	3)List（列表）
	
		Redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）。
	
		常用命令行参数：
		
		
			命令均以首字母L或者R开头
			
			LPUSH key value1 [value2]
			将一个或多个值从左插入到列表头部
			LPUSHX key value
			将一个值从左插入到已存在的列表头部
			RPUSH key value1 [value2]
			将一个或多个值从右插入到列表尾部
			RPUSHX key value
			将一个值从右插入到已存在的列表尾部
			LLEN key
			获取列表长度
			LPOP key
			移出并获取列表的第一个元素
			RPOP key
			移除并获取列表最后一个元素
			LINDEX key index
			通过索引获取列表中的元素
			LSET key index value
			通过索引设置列表元素的值
			LRANGE key start stop
			获取列表指定范围内的元素
			LREM key count value
			移除列表元素(记忆为left remove)
			
	
	4)Set（集合）
		
		1.Redis的Set是string类型的无序集合。
		2.集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。
		
		常用命令行参数：
		
			命令均以首字母s开头
			
			SADD key member1 [member2]
			向集合添加一个或多个成员
			SCARD key
			获取集合的成员数
			SISMEMBER key member
			判断 member 元素是否是集合 key 的成员（是的话返回1，否则返回0）
			SMEMBERS key
			返回集合中的所有成员
			SREM key member1 [member2]
			移除集合中一个或多个成员（返回删除数量）
			SPOP key
			移除并返回集合中的一个随机元素
	
	5)zset(sorted set：有序集合)
		
		1.Redis zset 和 set 一样也是string类型元素的集合,且不允许重复的成员。
		2.不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
		3.zset的成员是唯一的,但分数(score)却可以重复。
		
		常用命令行参数：
		
			命令区别于set均已z开头。
			
			ZADD key score1 member1 [score2 member2]
			向有序集合添加一个或多个成员，或者更新已存在成员的分数
			ZRANGE key start stop [WITHSCORES]
			通过索引区间返回有序集合成指定区间内的成员(0 -1分别可以对应第一个和最后一个元素)
			ZCARD key
			获取有序集合的成员数
			ZSCORE key member
			返回有序集中，成员的分数值
			ZRANK key member
			返回有序集合中指定成员的索引(从0开始，左至右)
			ZCOUNT key min max
			计算在有序集合中指定区间分数的成员数
			ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT]
			通过分数返回有序集合指定区间内的成员
			ZREVRANK key member
			返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序（PS:排名最高即分数最高的为0，当分数相同时，与存储顺序反向排序定位）
			ZREVRANGE key start stop [WITHSCORES]
			返回有序集中指定区间内的成员，通过索引，分数从高到底
			ZREVRANGEBYSCORE key max min [WITHSCORES]
			返回有序集中指定分数区间内的成员，分数从高到低排序
			ZREM key member [member ...]
			移除有序集合中的一个或多个成员
			
		
	
	
