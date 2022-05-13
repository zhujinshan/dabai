package com.dabai.proxy.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsePlateformInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UsePlateformInfoExample() {
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andPlateformIsNull() {
            addCriterion("plateform is null");
            return (Criteria) this;
        }

        public Criteria andPlateformIsNotNull() {
            addCriterion("plateform is not null");
            return (Criteria) this;
        }

        public Criteria andPlateformEqualTo(String value) {
            addCriterion("plateform =", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformNotEqualTo(String value) {
            addCriterion("plateform <>", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformGreaterThan(String value) {
            addCriterion("plateform >", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformGreaterThanOrEqualTo(String value) {
            addCriterion("plateform >=", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformLessThan(String value) {
            addCriterion("plateform <", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformLessThanOrEqualTo(String value) {
            addCriterion("plateform <=", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformLike(String value) {
            addCriterion("plateform like", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformNotLike(String value) {
            addCriterion("plateform not like", value, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformIn(List<String> values) {
            addCriterion("plateform in", values, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformNotIn(List<String> values) {
            addCriterion("plateform not in", values, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformBetween(String value1, String value2) {
            addCriterion("plateform between", value1, value2, "plateform");
            return (Criteria) this;
        }

        public Criteria andPlateformNotBetween(String value1, String value2) {
            addCriterion("plateform not between", value1, value2, "plateform");
            return (Criteria) this;
        }

        public Criteria andIdentityTagIsNull() {
            addCriterion("identity_tag is null");
            return (Criteria) this;
        }

        public Criteria andIdentityTagIsNotNull() {
            addCriterion("identity_tag is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityTagEqualTo(Byte value) {
            addCriterion("identity_tag =", value, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagNotEqualTo(Byte value) {
            addCriterion("identity_tag <>", value, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagGreaterThan(Byte value) {
            addCriterion("identity_tag >", value, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagGreaterThanOrEqualTo(Byte value) {
            addCriterion("identity_tag >=", value, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagLessThan(Byte value) {
            addCriterion("identity_tag <", value, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagLessThanOrEqualTo(Byte value) {
            addCriterion("identity_tag <=", value, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagIn(List<Byte> values) {
            addCriterion("identity_tag in", values, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagNotIn(List<Byte> values) {
            addCriterion("identity_tag not in", values, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagBetween(Byte value1, Byte value2) {
            addCriterion("identity_tag between", value1, value2, "identityTag");
            return (Criteria) this;
        }

        public Criteria andIdentityTagNotBetween(Byte value1, Byte value2) {
            addCriterion("identity_tag not between", value1, value2, "identityTag");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIsNull() {
            addCriterion("organization_code is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIsNotNull() {
            addCriterion("organization_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeEqualTo(String value) {
            addCriterion("organization_code =", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotEqualTo(String value) {
            addCriterion("organization_code <>", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeGreaterThan(String value) {
            addCriterion("organization_code >", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("organization_code >=", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLessThan(String value) {
            addCriterion("organization_code <", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLessThanOrEqualTo(String value) {
            addCriterion("organization_code <=", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLike(String value) {
            addCriterion("organization_code like", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotLike(String value) {
            addCriterion("organization_code not like", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIn(List<String> values) {
            addCriterion("organization_code in", values, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotIn(List<String> values) {
            addCriterion("organization_code not in", values, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeBetween(String value1, String value2) {
            addCriterion("organization_code between", value1, value2, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotBetween(String value1, String value2) {
            addCriterion("organization_code not between", value1, value2, "organizationCode");
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