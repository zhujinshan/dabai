package com.dabai.proxy.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WalletInfoExample() {
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

        public Criteria andUseIdIsNull() {
            addCriterion("use_id is null");
            return (Criteria) this;
        }

        public Criteria andUseIdIsNotNull() {
            addCriterion("use_id is not null");
            return (Criteria) this;
        }

        public Criteria andUseIdEqualTo(Long value) {
            addCriterion("use_id =", value, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdNotEqualTo(Long value) {
            addCriterion("use_id <>", value, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdGreaterThan(Long value) {
            addCriterion("use_id >", value, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("use_id >=", value, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdLessThan(Long value) {
            addCriterion("use_id <", value, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdLessThanOrEqualTo(Long value) {
            addCriterion("use_id <=", value, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdIn(List<Long> values) {
            addCriterion("use_id in", values, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdNotIn(List<Long> values) {
            addCriterion("use_id not in", values, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdBetween(Long value1, Long value2) {
            addCriterion("use_id between", value1, value2, "useId");
            return (Criteria) this;
        }

        public Criteria andUseIdNotBetween(Long value1, Long value2) {
            addCriterion("use_id not between", value1, value2, "useId");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountIsNull() {
            addCriterion("available_amount is null");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountIsNotNull() {
            addCriterion("available_amount is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountEqualTo(BigDecimal value) {
            addCriterion("available_amount =", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountNotEqualTo(BigDecimal value) {
            addCriterion("available_amount <>", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountGreaterThan(BigDecimal value) {
            addCriterion("available_amount >", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("available_amount >=", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountLessThan(BigDecimal value) {
            addCriterion("available_amount <", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("available_amount <=", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountIn(List<BigDecimal> values) {
            addCriterion("available_amount in", values, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountNotIn(List<BigDecimal> values) {
            addCriterion("available_amount not in", values, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_amount between", value1, value2, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_amount not between", value1, value2, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountIsNull() {
            addCriterion("cashed_amount is null");
            return (Criteria) this;
        }

        public Criteria andCashedAmountIsNotNull() {
            addCriterion("cashed_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCashedAmountEqualTo(BigDecimal value) {
            addCriterion("cashed_amount =", value, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountNotEqualTo(BigDecimal value) {
            addCriterion("cashed_amount <>", value, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountGreaterThan(BigDecimal value) {
            addCriterion("cashed_amount >", value, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cashed_amount >=", value, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountLessThan(BigDecimal value) {
            addCriterion("cashed_amount <", value, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cashed_amount <=", value, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountIn(List<BigDecimal> values) {
            addCriterion("cashed_amount in", values, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountNotIn(List<BigDecimal> values) {
            addCriterion("cashed_amount not in", values, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cashed_amount between", value1, value2, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andCashedAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cashed_amount not between", value1, value2, "cashedAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNull() {
            addCriterion("total_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNotNull() {
            addCriterion("total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountEqualTo(BigDecimal value) {
            addCriterion("total_amount =", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotEqualTo(BigDecimal value) {
            addCriterion("total_amount <>", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThan(BigDecimal value) {
            addCriterion("total_amount >", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amount >=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThan(BigDecimal value) {
            addCriterion("total_amount <", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amount <=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIn(List<BigDecimal> values) {
            addCriterion("total_amount in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotIn(List<BigDecimal> values) {
            addCriterion("total_amount not in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amount between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amount not between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeIsNull() {
            addCriterion("last_cash_time is null");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeIsNotNull() {
            addCriterion("last_cash_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeEqualTo(Date value) {
            addCriterion("last_cash_time =", value, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeNotEqualTo(Date value) {
            addCriterion("last_cash_time <>", value, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeGreaterThan(Date value) {
            addCriterion("last_cash_time >", value, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_cash_time >=", value, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeLessThan(Date value) {
            addCriterion("last_cash_time <", value, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_cash_time <=", value, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeIn(List<Date> values) {
            addCriterion("last_cash_time in", values, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeNotIn(List<Date> values) {
            addCriterion("last_cash_time not in", values, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeBetween(Date value1, Date value2) {
            addCriterion("last_cash_time between", value1, value2, "lastCashTime");
            return (Criteria) this;
        }

        public Criteria andLastCashTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_cash_time not between", value1, value2, "lastCashTime");
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