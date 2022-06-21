
-- postQuantityStatisticInRecentMonths
DROP FUNCTION if exists postQuantityStatisticInRecentMonths;
CREATE
OR REPLACE FUNCTION postQuantityStatisticInRecentMonths()
RETURNS TABLE
(month integer,
year integer,
count bigint)
as $$

BEGIN

Drop view if exists month_year;
CREATE
OR REPLACE VIEW month_year AS
SELECT extract(MONTH FROM CURRENT_DATE - (val * INTERVAL '1 month')) ::integer AS month,
			extract(YEAR FROM CURRENT_DATE - (val * INTERVAL '1 month'))::integer AS year
FROM (VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11)) AS tempValues(val);

RETURN QUERY
select m.month as month, m.year as year, count(i.id) as count
from (select * from post p) as i
    right join month_year m
	on extract (MONTH FROM i.created_at) = m.month and extract (YEAR FROM i.created_at) = m.year
group by m.month, m.year
order by m.year asc, m.month asc;

END;
$$
LANGUAGE plpgsql;

--SELECT * FROM postQuantityStatisticInRecentMonths()


-- postQuantityStatisticInQuarters
DROP FUNCTION if exists postQuantityStatisticInQuarters;
CREATE
OR REPLACE FUNCTION postQuantityStatisticInQuarters()
RETURNS TABLE
(quarter integer,
year integer,
count bigint)
as $$

BEGIN

Drop view if exists quarter_year;
CREATE
OR REPLACE VIEW quarter_year AS
SELECT 
	extract(QUARTER FROM CURRENT_DATE - (val * INTERVAL '3 month'))::integer AS quarter, 
	extract(YEAR FROM CURRENT_DATE - (val * INTERVAL '3 month')) ::integer AS year
FROM (VALUES (0), (1), (2), (3)) AS tempValues(val);

RETURN QUERY
select m.quarter as quarter, m.year as year, count(i.id) as count
from (select * from post p) as i
    right join quarter_year m
	on extract (QUARTER FROM i.created_at) = m.quarter and extract (YEAR FROM i.created_at) = m.year
group by m.quarter, m.year
order by m.year asc, m.quarter asc;

END;
$$
LANGUAGE plpgsql;

--SELECT * FROM postQuantityStatisticInQuarters()


-- postQuantityStatisticInYears
DROP FUNCTION if exists postQuantityStatisticInYears;

CREATE
OR REPLACE FUNCTION postQuantityStatisticInYears()
RETURNS TABLE
(month integer,
year integer,
count bigint)
as $$

BEGIN

Drop view if exists list_year;
CREATE
OR REPLACE VIEW list_year AS
SELECT extract(YEAR FROM CURRENT_DATE - (val * INTERVAL '12 month')) ::integer AS year
FROM (VALUES (0), (1)) AS tempValues(val);

RETURN QUERY
select 0 as month, m.year as year, count(i.id) as count
from (select * from post p) as i
    right join list_year m
on extract (YEAR FROM i.created_at) = m.year
group by m.year
order by m.year asc;

END;
$$
LANGUAGE plpgsql;

--SELECT * FROM postQuantityStatisticInYears()


-- Select top accounts which have most followers
DROP FUNCTION if exists getTopAccountMostFollowers;

CREATE
OR REPLACE FUNCTION getTopAccountMostFollowers(limit_value int)
RETURNS TABLE
(accountId bigint,
username character varying(255),
url character varying(255),
accountquantity bigint)
as $$

BEGIN

RETURN QUERY
	SELECT
		f.to_account accountId,
		a.username,
		att.url,
		COUNT(f.id) accountQuantity
	FROM
		account a
	INNER JOIN
		"following" f
	ON 
		a.id = f.to_account
	LEFT JOIN
		attachment att
	ON
		a.id = att.profile_id
	GROUP BY
		f.to_account,
		a.username,
		att.url
	ORDER BY
		accountQuantity DESC
	LIMIT limit_value
	;

END;
$$
LANGUAGE plpgsql;

--SELECT * FROM getTopAccountMostFollowers(50)


-- Select top posts which have most interactions
DROP FUNCTION if exists getTopPostInteraction;

CREATE
OR REPLACE FUNCTION getTopPostInteraction(limit_value int)
RETURNS TABLE
(postId bigint,
caption character varying(255),
created_at timestamp without time zone,
username character varying(255),
fullname character varying(255),
url character varying(255),
totalComment bigint,
totalLike bigint,
totalInteraction bigint)
as $$

BEGIN

RETURN QUERY
	SELECT
		p.id postId,
		p.caption,
		p.created_at,
		a.username,
		a.full_name,
		att.url,
		totalCommentCount.commentQuantity totalComment,
		totalLikeCount.likeQuantity totalLike,
		(totalCommentCount.commentQuantity + totalLikeCount.likeQuantity) totalInteraction
	FROM
		post p
		INNER JOIN
		(
			SELECT
				p.id postId,
				COUNT(c.id) commentQuantity
			FROM
				post p
			INNER JOIN
				"comment" c
			ON 
				p.id = c.post_id
			GROUP BY
				p.id
		) totalCommentCount
		ON 
			p.id = totalCommentCount.postId
		INNER JOIN
		(
			SELECT
				p.id postId,
				COUNT(l.id) likeQuantity
			FROM
				post p
			INNER JOIN
				liking l
			ON 
				p.id = l.post_id
			GROUP BY
				p.id
		) totalLikeCount
		ON 
			p.id = totalLikeCount.postId
		INNER JOIN
			account a
		ON
			a.id = p.created_by_account_id
		LEFT JOIN
			attachment att
		ON
			att.profile_id = a.id
	ORDER BY
		totalInteraction DESC
	LIMIT limit_value
	;

END;
$$
LANGUAGE plpgsql;

--SELECT * FROM getTopPostInteraction(20)


-- Select newest posts
DROP FUNCTION if exists getTopNewestPost;

CREATE
OR REPLACE FUNCTION getTopNewestPost(limit_value int)
RETURNS TABLE
(id bigint,
active boolean,
created_at timestamp without time zone,
last_modified_at timestamp without time zone,
caption character varying(1500),
privacy integer,
username character varying(255),
full_name character varying(255),
url character varying(255)
)
as $$

BEGIN

RETURN QUERY
	SELECT
		p.id,
		p.active,
		p.created_at,
		p.last_modified_at,
		p.caption,
		p.privacy,
		a.username,
		a.full_name,
		att.url
	FROM
		post p
	INNER JOIN
		account a
	ON
		p.created_by_account_id = a.id
	LEFT JOIN
		attachment att
	ON
		p.created_by_account_id = att.profile_id
	ORDER BY
		p.created_at DESC
	LIMIT limit_value
	;

END;
$$
LANGUAGE plpgsql;

--SELECT * FROM getTopNewestPost(10)






