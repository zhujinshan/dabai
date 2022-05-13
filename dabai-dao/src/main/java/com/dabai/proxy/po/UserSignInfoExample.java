package com.dabai.proxy.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSignInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserSignInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSignSourceIsNull() {
            addCriterion("sign_source is null");
            return (Criteria) this;
        }

        public Criteria andSignSourceIsNotNull() {
            addCriterion("sign_source is not null");
            return (Criteria) this;
        }

        public Criteria andSignSourceEqualTo(String value) {
            addCriterion("sign_source =", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceNotEqualTo(String value) {
            addCriterion("sign_source <>", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceGreaterThan(String value) {
            addCriterion("sign_source >", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceGreaterThanOrEqualTo(String value) {
            addCriterion("sign_source >=", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceLessThan(String value) {
            addCriterion("sign_source <", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceLessThanOrEqualTo(String value) {
            addCriterion("sign_source <=", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceLike(String value) {
            addCriterion("sign_source like", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceNotLike(String value) {
            addCriterion("sign_source not like", value, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceIn(List<String> values) {
            addCriterion("sign_source in", values, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceNotIn(List<String> values) {
            addCriterion("sign_source not in", values, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceBetween(String value1, String value2) {
            addCriterion("sign_source between", value1, value2, "signSource");
            return (Criteria) this;
        }

        public Criteria andSignSourceNotBetween(String value1, String value2) {
            addCriterion("sign_source not between", value1, value2, "signSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceIsNull() {
            addCriterion("business_source is null");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceIsNotNull() {
            addCriterion("business_source is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceEqualTo(String value) {
            addCriterion("business_source =", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceNotEqualTo(String value) {
            addCriterion("business_source <>", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceGreaterThan(String value) {
            addCriterion("business_source >", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceGreaterThanOrEqualTo(String value) {
            addCriterion("business_source >=", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceLessThan(String value) {
            addCriterion("business_source <", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceLessThanOrEqualTo(String value) {
            addCriterion("business_source <=", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceLike(String value) {
            addCriterion("business_source like", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceNotLike(String value) {
            addCriterion("business_source not like", value, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceIn(List<String> values) {
            addCriterion("business_source in", values, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceNotIn(List<String> values) {
            addCriterion("business_source not in", values, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceBetween(String value1, String value2) {
            addCriterion("business_source between", value1, value2, "businessSource");
            return (Criteria) this;
        }

        public Criteria andBusinessSourceNotBetween(String value1, String value2) {
            addCriterion("business_source not between", value1, value2, "businessSource");
            return (Criteria) this;
        }

        public Criteria andSignStatusIsNull() {
            addCriterion("sign_status is null");
            return (Criteria) this;
        }

        public Criteria andSignStatusIsNotNull() {
            addCriterion("sign_status is not null");
            return (Criteria) this;
        }

        public Criteria andSignStatusEqualTo(Byte value) {
            addCriterion("sign_status =", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusNotEqualTo(Byte value) {
            addCriterion("sign_status <>", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusGreaterThan(Byte value) {
            addCriterion("sign_status >", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("sign_status >=", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusLessThan(Byte value) {
            addCriterion("sign_status <", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusLessThanOrEqualTo(Byte value) {
            addCriterion("sign_status <=", value, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusIn(List<Byte> values) {
            addCriterion("sign_status in", values, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusNotIn(List<Byte> values) {
            addCriterion("sign_status not in", values, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusBetween(Byte value1, Byte value2) {
            addCriterion("sign_status between", value1, value2, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("sign_status not between", value1, value2, "signStatus");
            return (Criteria) this;
        }

        public Criteria andSignDealNoIsNull() {
            addCriterion("sign_deal_no is null");
            return (Criteria) this;
        }

        public Criteria andSignDealNoIsNotNull() {
            addCriterion("sign_deal_no is not null");
            return (Criteria) this;
        }

        public Criteria andSignDealNoEqualTo(String value) {
            addCriterion("sign_deal_no =", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoNotEqualTo(String value) {
            addCriterion("sign_deal_no <>", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoGreaterThan(String value) {
            addCriterion("sign_deal_no >", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoGreaterThanOrEqualTo(String value) {
            addCriterion("sign_deal_no >=", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoLessThan(String value) {
            addCriterion("sign_deal_no <", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoLessThanOrEqualTo(String value) {
            addCriterion("sign_deal_no <=", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoLike(String value) {
            addCriterion("sign_deal_no like", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoNotLike(String value) {
            addCriterion("sign_deal_no not like", value, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoIn(List<String> values) {
            addCriterion("sign_deal_no in", values, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoNotIn(List<String> values) {
            addCriterion("sign_deal_no not in", values, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoBetween(String value1, String value2) {
            addCriterion("sign_deal_no between", value1, value2, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andSignDealNoNotBetween(String value1, String value2) {
            addCriterion("sign_deal_no not between", value1, value2, "signDealNo");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCtimeIsNull() {
            addCriterion("ctime is null");
            return (Criteria) this;
        }

        public Criteria andCtimeIsNotNull() {
            addCriterion("ctime is not null");
            return (Criteria) this;
        }

        public Criteria andCtimeEqualTo(Date value) {
            addCriterion("ctime =", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotEqualTo(Date value) {
            addCriterion("ctime <>", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeGreaterThan(Date value) {
            addCriterion("ctime >", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ctime >=", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeLessThan(Date value) {
            addCriterion("ctime <", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeLessThanOrEqualTo(Date value) {
            addCriterion("ctime <=", value, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeIn(List<Date> values) {
            addCriterion("ctime in", values, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotIn(List<Date> values) {
            addCriterion("ctime not in", values, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeBetween(Date value1, Date value2) {
            addCriterion("ctime between", value1, value2, "ctime");
            return (Criteria) this;
        }

        public Criteria andCtimeNotBetween(Date value1, Date value2) {
            addCriterion("ctime not between", value1, value2, "ctime");
            return (Criteria) this;
        }

        public Criteria andUtimeIsNull() {
            addCriterion("utime is null");
            return (Criteria) this;
        }

        public Criteria andUtimeIsNotNull() {
            addCriterion("utime is not null");
            return (Criteria) this;
        }

        public Criteria andUtimeEqualTo(Date value) {
            addCriterion("utime =", value, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeNotEqualTo(Date value) {
            addCriterion("utime <>", value, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeGreaterThan(Date value) {
            addCriterion("utime >", value, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("utime >=", value, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeLessThan(Date value) {
            addCriterion("utime <", value, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeLessThanOrEqualTo(Date value) {
            addCriterion("utime <=", value, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeIn(List<Date> values) {
            addCriterion("utime in", values, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeNotIn(List<Date> values) {
            addCriterion("utime not in", values, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeBetween(Date value1, Date value2) {
            addCriterion("utime between", value1, value2, "utime");
            return (Criteria) this;
        }

        public Criteria andUtimeNotBetween(Date value1, Date value2) {
            addCriterion("utime not between", value1, value2, "utime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}