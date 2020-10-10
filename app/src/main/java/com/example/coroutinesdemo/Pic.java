package com.example.coroutinesdemo;

import java.util.List;

public class Pic {

    /**
     * log_id : 3591049593939822907
     * item : {"lv2_tag_list":[{"score":0.877436,"tag":"足球"},{"score":0.793682,"tag":"国际足球"},{"score":0.775911,"tag":"英超"}],"lv1_tag_list":[{"score":0.824329,"tag":"体育"}]}
     */

    private long log_id;
    private ItemBean item;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        private List<Lv2TagListBean> lv2_tag_list;
        private List<Lv1TagListBean> lv1_tag_list;

        public List<Lv2TagListBean> getLv2_tag_list() {
            return lv2_tag_list;
        }

        public void setLv2_tag_list(List<Lv2TagListBean> lv2_tag_list) {
            this.lv2_tag_list = lv2_tag_list;
        }

        public List<Lv1TagListBean> getLv1_tag_list() {
            return lv1_tag_list;
        }

        public void setLv1_tag_list(List<Lv1TagListBean> lv1_tag_list) {
            this.lv1_tag_list = lv1_tag_list;
        }

        public static class Lv2TagListBean {
            /**
             * score : 0.877436
             * tag : 足球
             */

            private double score;
            private String tag;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }
        }

        public static class Lv1TagListBean {
            /**
             * score : 0.824329
             * tag : 体育
             */

            private double score;
            private String tag;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }
        }
    }
}
