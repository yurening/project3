package com.stylefeng.guns.rest.common.persistence.model;

import java.io.Serializable;
import java.util.List;

public class ConditionReturn implements Serializable {

    private static final long serialVersionUID = -1102818532956906350L;

    public static class DataBean implements Serializable {
        private static final long serialVersionUID = 8839663643639198899L;
        private List<CatInfoBean> catInfo;
        private List<SourceInfoBean> sourceInfo;
        private List<YearInfoBean> yearInfo;

        public List<CatInfoBean> getCatInfo() {
            return catInfo;
        }

        public void setCatInfo(List<CatInfoBean> catInfo) {
            this.catInfo = catInfo;
        }

        public List<SourceInfoBean> getSourceInfo() {
            return sourceInfo;
        }

        public void setSourceInfo(List<SourceInfoBean> sourceInfo) {
            this.sourceInfo = sourceInfo;
        }

        public List<YearInfoBean> getYearInfo() {
            return yearInfo;
        }

        public void setYearInfo(List<YearInfoBean> yearInfo) {
            this.yearInfo = yearInfo;
        }

        public static class CatInfoBean implements Serializable {
            private static final long serialVersionUID = -8375887204449730431L;
            /**
             * active : false
             * catId : 1
             * catName : 爱情
             */

            private boolean active;
            private String catId;
            private String catName;

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }

            public String getCatId() {
                return catId;
            }

            public void setCatId(String catId) {
                this.catId = catId;
            }

            public String getCatName() {
                return catName;
            }

            public void setCatName(String catName) {
                this.catName = catName;
            }
        }

        public static class SourceInfoBean implements Serializable {
            private static final long serialVersionUID = -3008842986013282539L;
            /**
             * active : false
             * sourceId : 1
             * sourceName : 大陆
             */

            private boolean active;
            private String sourceId;
            private String sourceName;

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }

            public String getSourceId() {
                return sourceId;
            }

            public void setSourceId(String sourceId) {
                this.sourceId = sourceId;
            }

            public String getSourceName() {
                return sourceName;
            }

            public void setSourceName(String sourceName) {
                this.sourceName = sourceName;
            }
        }

        public static class YearInfoBean implements Serializable {
            private static final long serialVersionUID = -2012741439023772145L;
            /**
             * active : false
             * yearId : 1
             * yearName : 更早
             */

            private boolean active;
            private String yearId;
            private String yearName;

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }

            public String getYearId() {
                return yearId;
            }

            public void setYearId(String yearId) {
                this.yearId = yearId;
            }

            public String getYearName() {
                return yearName;
            }

            public void setYearName(String yearName) {
                this.yearName = yearName;
            }
        }
    }
}
