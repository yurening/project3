package com.stylefeng.guns.rest.common.persistence.model;

import java.io.Serializable;
import java.util.List;

public class FirstPageReturn  implements Serializable {

    private static final long serialVersionUID = 710406470676784123L;

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 5850136985167904415L;
        /**
         * banners : [{"bannerAddress":"banner1.png","bannerId":"2","bannerUrl":"www.meetingshop.cn"},{"bannerAddress":"banner2.png","bannerId":"3","bannerUrl":"www.meetingshop.cn"},{"bannerAddress":"banner3.png","bannerId":"4","bannerUrl":"www.meetingshop.cn"}]
         * boxRanking : [{"boxNum":123421,"expectNum":234235,"filmCats":"","filmId":"13","filmLength":"","filmName":"素人特工","filmScore":"8.0","filmType":0,"imgAddress":"c0bec212d759ad52f22bbb280e551c065000875.jpg","score":"8.0","showTime":"2018-07-12"},{"boxNum":309600,"expectNum":231432491,"filmCats":"","filmId":"2","filmLength":"","filmName":"我不是药神","filmScore":"9.7","filmType":0,"imgAddress":"238e2dc36beae55a71cabfc14069fe78236351.jpg","score":"9.7","showTime":"2018-07-05"},{"boxNum":464563,"expectNum":5467523,"filmCats":"","filmId":"10","filmLength":"","filmName":"命运之夜","filmScore":"9.0","filmType":1,"imgAddress":"7b40e56e644cd04915e6e9cc09c1bdb1331242.jpg","score":"9.0","showTime":"2018-07-12"},{"boxNum":534231,"expectNum":323452,"filmCats":"","filmId":"15","filmLength":"","filmName":"看不见的你","filmScore":"8.5","filmType":1,"imgAddress":"untold_main.jpg","score":"8.5","showTime":"2018-07-19"},{"boxNum":767654,"expectNum":545241,"filmCats":"","filmId":"16","filmLength":"","filmName":"使徒行者","filmScore":"8.6","filmType":2,"imgAddress":"walker_main.jpg","score":"8.6","showTime":"2018-07-19"},{"boxNum":3252425,"expectNum":23453,"filmCats":"","filmId":"14","filmLength":"","filmName":"回到过去拥抱你","filmScore":"8.1","filmType":1,"imgAddress":"huidaoguoqu_main.jpg","score":"8.1","showTime":"2018-07-16"},{"boxNum":24123155,"expectNum":43756734,"filmCats":"","filmId":"11","filmLength":"","filmName":"狮子王","filmScore":"9.5","filmType":3,"imgAddress":"7b9b0725ab5feae642e1fbba9fbb90fe3702078.jpg","score":"9.5","showTime":"2018-07-12"},{"boxNum":33546234,"expectNum":86756434,"filmCats":"","filmId":"9","filmLength":"","filmName":"蜘蛛侠英雄远征","filmScore":"8.0","filmType":2,"imgAddress":"5dac476535359b7bb951ff15d37a9d0b153821.jpg","score":"8.0","showTime":"2018-07-12"},{"boxNum":45364642,"expectNum":1227657,"filmCats":"","filmId":"12","filmLength":"","filmName":"扫毒2","filmScore":"8.2","filmType":2,"imgAddress":"8d3efdc44af04c3254fc9e4ad5334ae32660685.jpg","score":"8.2","showTime":"2018-07-12"}]
         * expectRanking : [{"boxNum":23453452,"expectNum":32233,"filmCats":"","filmId":"8","filmLength":"","filmName":"怨灵3","filmScore":"7.7","filmType":1,"imgAddress":"d143039b1b72205ffb08b1de6ef38ffe1324616.jpg","score":"7.7","showTime":"2018-07-19"},{"boxNum":425352,"expectNum":349835,"filmCats":"","filmId":"3","filmLength":"","filmName":"跳舞吧大象","filmScore":"8.8","filmType":0,"imgAddress":"1813b306280c0f37f9812afbbe631ae33681369.jpg","score":"8.8","showTime":"2018-07-12"},{"boxNum":64565723,"expectNum":2343534,"filmCats":"","filmId":"7","filmLength":"","filmName":"未来机械城","filmScore":"8.2","filmType":2,"imgAddress":"bb9f75599bfbb2c4cf77ad9abae1b95c1376927.png","score":"8.2","showTime":"2018-07-19"},{"boxNum":657678,"expectNum":5475854,"filmCats":"","filmId":"6","filmLength":"","filmName":"银河补习班","filmScore":"8.0","filmType":2,"imgAddress":"bb9f75599bfbb2c4cf77ad9abae1b95c1376927.jpg","score":"8.0","showTime":"2018-07-13"},{"boxNum":34764524,"expectNum":23234234,"filmCats":"","filmId":"5","filmLength":"","filmName":"猪八戒传说","filmScore":"7.2","filmType":1,"imgAddress":"99b46395a2675e1cf3510032a1088fa54610159.jpg","score":"7.2","showTime":"2018-07-14"},{"boxNum":23456543,"expectNum":32567345,"filmCats":"","filmId":"4","filmLength":"","filmName":"机动战士高达","filmScore":"9.0","filmType":2,"imgAddress":"2bd43dce9113181254b2d21aa10929c29845750.jpg","score":"9.0","showTime":"2018-07-13"},{"boxNum":43534321,"expectNum":35535243,"filmCats":"","filmId":"17","filmLength":"","filmName":"速度与激情","filmScore":"9.2","filmType":1,"imgAddress":"fastandfate_main.jpg","score":"9.2","showTime":"2018-07-19"}]
         * hotFilms : {"filmInfo":[{"boxNum":123421,"expectNum":234235,"filmCats":"","filmId":"13","filmLength":"","filmName":"素人特工","filmScore":"8.0","filmType":0,"imgAddress":"c0bec212d759ad52f22bbb280e551c065000875.jpg","score":"8.0","showTime":"2018-07-12"},{"boxNum":309600,"expectNum":231432491,"filmCats":"","filmId":"2","filmLength":"","filmName":"我不是药神","filmScore":"9.7","filmType":0,"imgAddress":"238e2dc36beae55a71cabfc14069fe78236351.jpg","score":"9.7","showTime":"2018-07-05"},{"boxNum":464563,"expectNum":5467523,"filmCats":"","filmId":"10","filmLength":"","filmName":"命运之夜","filmScore":"9.0","filmType":1,"imgAddress":"7b40e56e644cd04915e6e9cc09c1bdb1331242.jpg","score":"9.0","showTime":"2018-07-12"},{"boxNum":534231,"expectNum":323452,"filmCats":"","filmId":"15","filmLength":"","filmName":"看不见的你","filmScore":"8.5","filmType":1,"imgAddress":"untold_main.jpg","score":"8.5","showTime":"2018-07-19"},{"boxNum":767654,"expectNum":545241,"filmCats":"","filmId":"16","filmLength":"","filmName":"使徒行者","filmScore":"8.6","filmType":2,"imgAddress":"walker_main.jpg","score":"8.6","showTime":"2018-07-19"},{"boxNum":3252425,"expectNum":23453,"filmCats":"","filmId":"14","filmLength":"","filmName":"回到过去拥抱你","filmScore":"8.1","filmType":1,"imgAddress":"huidaoguoqu_main.jpg","score":"8.1","showTime":"2018-07-16"},{"boxNum":24123155,"expectNum":43756734,"filmCats":"","filmId":"11","filmLength":"","filmName":"狮子王","filmScore":"9.5","filmType":3,"imgAddress":"7b9b0725ab5feae642e1fbba9fbb90fe3702078.jpg","score":"9.5","showTime":"2018-07-12"},{"boxNum":33546234,"expectNum":86756434,"filmCats":"","filmId":"9","filmLength":"","filmName":"蜘蛛侠英雄远征","filmScore":"8.0","filmType":2,"imgAddress":"5dac476535359b7bb951ff15d37a9d0b153821.jpg","score":"8.0","showTime":"2018-07-12"}],"filmNum":8,"nowPage":"","totalPage":""}
         * soonFilms : {"filmInfo":[{"boxNum":23453452,"expectNum":32233,"filmCats":"","filmId":"8","filmLength":"","filmName":"怨灵3","filmScore":"7.7","filmType":1,"imgAddress":"d143039b1b72205ffb08b1de6ef38ffe1324616.jpg","score":"7.7","showTime":"2018-07-19"},{"boxNum":425352,"expectNum":349835,"filmCats":"","filmId":"3","filmLength":"","filmName":"跳舞吧大象","filmScore":"8.8","filmType":0,"imgAddress":"1813b306280c0f37f9812afbbe631ae33681369.jpg","score":"8.8","showTime":"2018-07-12"},{"boxNum":64565723,"expectNum":2343534,"filmCats":"","filmId":"7","filmLength":"","filmName":"未来机械城","filmScore":"8.2","filmType":2,"imgAddress":"bb9f75599bfbb2c4cf77ad9abae1b95c1376927.png","score":"8.2","showTime":"2018-07-19"},{"boxNum":657678,"expectNum":5475854,"filmCats":"","filmId":"6","filmLength":"","filmName":"银河补习班","filmScore":"8.0","filmType":2,"imgAddress":"bb9f75599bfbb2c4cf77ad9abae1b95c1376927.jpg","score":"8.0","showTime":"2018-07-13"},{"boxNum":34764524,"expectNum":23234234,"filmCats":"","filmId":"5","filmLength":"","filmName":"猪八戒传说","filmScore":"7.2","filmType":1,"imgAddress":"99b46395a2675e1cf3510032a1088fa54610159.jpg","score":"7.2","showTime":"2018-07-14"},{"boxNum":23456543,"expectNum":32567345,"filmCats":"","filmId":"4","filmLength":"","filmName":"机动战士高达","filmScore":"9.0","filmType":2,"imgAddress":"2bd43dce9113181254b2d21aa10929c29845750.jpg","score":"9.0","showTime":"2018-07-13"},{"boxNum":43534321,"expectNum":35535243,"filmCats":"","filmId":"17","filmLength":"","filmName":"速度与激情","filmScore":"9.2","filmType":1,"imgAddress":"fastandfate_main.jpg","score":"9.2","showTime":"2018-07-19"}],"filmNum":7,"nowPage":"","totalPage":""}
         * top100 : [{"boxNum":33546234,"expectNum":86756434,"filmCats":"","filmId":"9","filmLength":"","filmName":"蜘蛛侠英雄远征","filmScore":"8.0","filmType":2,"imgAddress":"5dac476535359b7bb951ff15d37a9d0b153821.jpg","score":"8.0","showTime":"2018-07-12"},{"boxNum":123421,"expectNum":234235,"filmCats":"","filmId":"13","filmLength":"","filmName":"素人特工","filmScore":"8.0","filmType":0,"imgAddress":"c0bec212d759ad52f22bbb280e551c065000875.jpg","score":"8.0","showTime":"2018-07-12"},{"boxNum":3252425,"expectNum":23453,"filmCats":"","filmId":"14","filmLength":"","filmName":"回到过去拥抱你","filmScore":"8.1","filmType":1,"imgAddress":"huidaoguoqu_main.jpg","score":"8.1","showTime":"2018-07-16"},{"boxNum":45364642,"expectNum":1227657,"filmCats":"","filmId":"12","filmLength":"","filmName":"扫毒2","filmScore":"8.2","filmType":2,"imgAddress":"8d3efdc44af04c3254fc9e4ad5334ae32660685.jpg","score":"8.2","showTime":"2018-07-12"},{"boxNum":534231,"expectNum":323452,"filmCats":"","filmId":"15","filmLength":"","filmName":"看不见的你","filmScore":"8.5","filmType":1,"imgAddress":"untold_main.jpg","score":"8.5","showTime":"2018-07-19"},{"boxNum":767654,"expectNum":545241,"filmCats":"","filmId":"16","filmLength":"","filmName":"使徒行者","filmScore":"8.6","filmType":2,"imgAddress":"walker_main.jpg","score":"8.6","showTime":"2018-07-19"},{"boxNum":464563,"expectNum":5467523,"filmCats":"","filmId":"10","filmLength":"","filmName":"命运之夜","filmScore":"9.0","filmType":1,"imgAddress":"7b40e56e644cd04915e6e9cc09c1bdb1331242.jpg","score":"9.0","showTime":"2018-07-12"},{"boxNum":24123155,"expectNum":43756734,"filmCats":"","filmId":"11","filmLength":"","filmName":"狮子王","filmScore":"9.5","filmType":3,"imgAddress":"7b9b0725ab5feae642e1fbba9fbb90fe3702078.jpg","score":"9.5","showTime":"2018-07-12"},{"boxNum":309600,"expectNum":231432491,"filmCats":"","filmId":"2","filmLength":"","filmName":"我不是药神","filmScore":"9.7","filmType":0,"imgAddress":"238e2dc36beae55a71cabfc14069fe78236351.jpg","score":"9.7","showTime":"2018-07-05"}]
         */

        private HotFilmsBean hotFilms;
        private SoonFilmsBean soonFilms;
        private List<BannersBean> banners;
        private List<BoxRankingBean> boxRanking;
        private List<ExpectRankingBean> expectRanking;
        private List<Top100Bean> top100;

        public HotFilmsBean getHotFilms() {
            return hotFilms;
        }

        public void setHotFilms(HotFilmsBean hotFilms) {
            this.hotFilms = hotFilms;
        }

        public SoonFilmsBean getSoonFilms() {
            return soonFilms;
        }

        public void setSoonFilms(SoonFilmsBean soonFilms) {
            this.soonFilms = soonFilms;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public List<BoxRankingBean> getBoxRanking() {
            return boxRanking;
        }

        public void setBoxRanking(List<BoxRankingBean> boxRanking) {
            this.boxRanking = boxRanking;
        }

        public List<ExpectRankingBean> getExpectRanking() {
            return expectRanking;
        }

        public void setExpectRanking(List<ExpectRankingBean> expectRanking) {
            this.expectRanking = expectRanking;
        }

        public List<Top100Bean> getTop100() {
            return top100;
        }

        public void setTop100(List<Top100Bean> top100) {
            this.top100 = top100;
        }

        public static class HotFilmsBean implements Serializable{
            private static final long serialVersionUID = 3959285690389344070L;
            /**
             * filmInfo : [{"boxNum":123421,"expectNum":234235,"filmCats":"","filmId":"13","filmLength":"","filmName":"素人特工","filmScore":"8.0","filmType":0,"imgAddress":"c0bec212d759ad52f22bbb280e551c065000875.jpg","score":"8.0","showTime":"2018-07-12"},{"boxNum":309600,"expectNum":231432491,"filmCats":"","filmId":"2","filmLength":"","filmName":"我不是药神","filmScore":"9.7","filmType":0,"imgAddress":"238e2dc36beae55a71cabfc14069fe78236351.jpg","score":"9.7","showTime":"2018-07-05"},{"boxNum":464563,"expectNum":5467523,"filmCats":"","filmId":"10","filmLength":"","filmName":"命运之夜","filmScore":"9.0","filmType":1,"imgAddress":"7b40e56e644cd04915e6e9cc09c1bdb1331242.jpg","score":"9.0","showTime":"2018-07-12"},{"boxNum":534231,"expectNum":323452,"filmCats":"","filmId":"15","filmLength":"","filmName":"看不见的你","filmScore":"8.5","filmType":1,"imgAddress":"untold_main.jpg","score":"8.5","showTime":"2018-07-19"},{"boxNum":767654,"expectNum":545241,"filmCats":"","filmId":"16","filmLength":"","filmName":"使徒行者","filmScore":"8.6","filmType":2,"imgAddress":"walker_main.jpg","score":"8.6","showTime":"2018-07-19"},{"boxNum":3252425,"expectNum":23453,"filmCats":"","filmId":"14","filmLength":"","filmName":"回到过去拥抱你","filmScore":"8.1","filmType":1,"imgAddress":"huidaoguoqu_main.jpg","score":"8.1","showTime":"2018-07-16"},{"boxNum":24123155,"expectNum":43756734,"filmCats":"","filmId":"11","filmLength":"","filmName":"狮子王","filmScore":"9.5","filmType":3,"imgAddress":"7b9b0725ab5feae642e1fbba9fbb90fe3702078.jpg","score":"9.5","showTime":"2018-07-12"},{"boxNum":33546234,"expectNum":86756434,"filmCats":"","filmId":"9","filmLength":"","filmName":"蜘蛛侠英雄远征","filmScore":"8.0","filmType":2,"imgAddress":"5dac476535359b7bb951ff15d37a9d0b153821.jpg","score":"8.0","showTime":"2018-07-12"}]
             * filmNum : 8
             * nowPage :
             * totalPage :
             */

            private int filmNum;
            private String nowPage;
            private String totalPage;
            private List<FilmInfoBean> filmInfo;

            public int getFilmNum() {
                return filmNum;
            }

            public void setFilmNum(int filmNum) {
                this.filmNum = filmNum;
            }

            public String getNowPage() {
                return nowPage;
            }

            public void setNowPage(String nowPage) {
                this.nowPage = nowPage;
            }

            public String getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(String totalPage) {
                this.totalPage = totalPage;
            }

            public List<FilmInfoBean> getFilmInfo() {
                return filmInfo;
            }

            public void setFilmInfo(List<FilmInfoBean> filmInfo) {
                this.filmInfo = filmInfo;
            }

            public static class FilmInfoBean implements Serializable{
                private static final long serialVersionUID = 2303719124373603867L;
                /**
                 * boxNum : 123421
                 * expectNum : 234235
                 * filmCats :
                 * filmId : 13
                 * filmLength :
                 * filmName : 素人特工
                 * filmScore : 8.0
                 * filmType : 0
                 * imgAddress : c0bec212d759ad52f22bbb280e551c065000875.jpg
                 * score : 8.0
                 * showTime : 2018-07-12
                 */

                private int boxNum;
                private int expectNum;
                private String filmCats;
                private String filmId;
                private String filmLength;
                private String filmName;
                private String filmScore;
                private int filmType;
                private String imgAddress;
                private String score;
                private String showTime;

                public int getBoxNum() {
                    return boxNum;
                }

                public void setBoxNum(int boxNum) {
                    this.boxNum = boxNum;
                }

                public int getExpectNum() {
                    return expectNum;
                }

                public void setExpectNum(int expectNum) {
                    this.expectNum = expectNum;
                }

                public String getFilmCats() {
                    return filmCats;
                }

                public void setFilmCats(String filmCats) {
                    this.filmCats = filmCats;
                }

                public String getFilmId() {
                    return filmId;
                }

                public void setFilmId(String filmId) {
                    this.filmId = filmId;
                }

                public String getFilmLength() {
                    return filmLength;
                }

                public void setFilmLength(String filmLength) {
                    this.filmLength = filmLength;
                }

                public String getFilmName() {
                    return filmName;
                }

                public void setFilmName(String filmName) {
                    this.filmName = filmName;
                }

                public String getFilmScore() {
                    return filmScore;
                }

                public void setFilmScore(String filmScore) {
                    this.filmScore = filmScore;
                }

                public int getFilmType() {
                    return filmType;
                }

                public void setFilmType(int filmType) {
                    this.filmType = filmType;
                }

                public String getImgAddress() {
                    return imgAddress;
                }

                public void setImgAddress(String imgAddress) {
                    this.imgAddress = imgAddress;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getShowTime() {
                    return showTime;
                }

                public void setShowTime(String showTime) {
                    this.showTime = showTime;
                }
            }
        }

        public static class SoonFilmsBean implements Serializable{
            private static final long serialVersionUID = -9150832887611932660L;
            /**
             * filmInfo : [{"boxNum":23453452,"expectNum":32233,"filmCats":"","filmId":"8","filmLength":"","filmName":"怨灵3","filmScore":"7.7","filmType":1,"imgAddress":"d143039b1b72205ffb08b1de6ef38ffe1324616.jpg","score":"7.7","showTime":"2018-07-19"},{"boxNum":425352,"expectNum":349835,"filmCats":"","filmId":"3","filmLength":"","filmName":"跳舞吧大象","filmScore":"8.8","filmType":0,"imgAddress":"1813b306280c0f37f9812afbbe631ae33681369.jpg","score":"8.8","showTime":"2018-07-12"},{"boxNum":64565723,"expectNum":2343534,"filmCats":"","filmId":"7","filmLength":"","filmName":"未来机械城","filmScore":"8.2","filmType":2,"imgAddress":"bb9f75599bfbb2c4cf77ad9abae1b95c1376927.png","score":"8.2","showTime":"2018-07-19"},{"boxNum":657678,"expectNum":5475854,"filmCats":"","filmId":"6","filmLength":"","filmName":"银河补习班","filmScore":"8.0","filmType":2,"imgAddress":"bb9f75599bfbb2c4cf77ad9abae1b95c1376927.jpg","score":"8.0","showTime":"2018-07-13"},{"boxNum":34764524,"expectNum":23234234,"filmCats":"","filmId":"5","filmLength":"","filmName":"猪八戒传说","filmScore":"7.2","filmType":1,"imgAddress":"99b46395a2675e1cf3510032a1088fa54610159.jpg","score":"7.2","showTime":"2018-07-14"},{"boxNum":23456543,"expectNum":32567345,"filmCats":"","filmId":"4","filmLength":"","filmName":"机动战士高达","filmScore":"9.0","filmType":2,"imgAddress":"2bd43dce9113181254b2d21aa10929c29845750.jpg","score":"9.0","showTime":"2018-07-13"},{"boxNum":43534321,"expectNum":35535243,"filmCats":"","filmId":"17","filmLength":"","filmName":"速度与激情","filmScore":"9.2","filmType":1,"imgAddress":"fastandfate_main.jpg","score":"9.2","showTime":"2018-07-19"}]
             * filmNum : 7
             * nowPage :
             * totalPage :
             */

            private int filmNum;
            private String nowPage;
            private String totalPage;
            private List<FilmInfoBeanX> filmInfo;

            public int getFilmNum() {
                return filmNum;
            }

            public void setFilmNum(int filmNum) {
                this.filmNum = filmNum;
            }

            public String getNowPage() {
                return nowPage;
            }

            public void setNowPage(String nowPage) {
                this.nowPage = nowPage;
            }

            public String getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(String totalPage) {
                this.totalPage = totalPage;
            }

            public List<FilmInfoBeanX> getFilmInfo() {
                return filmInfo;
            }

            public void setFilmInfo(List<FilmInfoBeanX> filmInfo) {
                this.filmInfo = filmInfo;
            }

            public static class FilmInfoBeanX implements Serializable{
                private static final long serialVersionUID = 3574887620374174457L;
                /**
                 * boxNum : 23453452
                 * expectNum : 32233
                 * filmCats :
                 * filmId : 8
                 * filmLength :
                 * filmName : 怨灵3
                 * filmScore : 7.7
                 * filmType : 1
                 * imgAddress : d143039b1b72205ffb08b1de6ef38ffe1324616.jpg
                 * score : 7.7
                 * showTime : 2018-07-19
                 */

                private int boxNum;
                private int expectNum;
                private String filmCats;
                private String filmId;
                private String filmLength;
                private String filmName;
                private String filmScore;
                private int filmType;
                private String imgAddress;
                private String score;
                private String showTime;

                public int getBoxNum() {
                    return boxNum;
                }

                public void setBoxNum(int boxNum) {
                    this.boxNum = boxNum;
                }

                public int getExpectNum() {
                    return expectNum;
                }

                public void setExpectNum(int expectNum) {
                    this.expectNum = expectNum;
                }

                public String getFilmCats() {
                    return filmCats;
                }

                public void setFilmCats(String filmCats) {
                    this.filmCats = filmCats;
                }

                public String getFilmId() {
                    return filmId;
                }

                public void setFilmId(String filmId) {
                    this.filmId = filmId;
                }

                public String getFilmLength() {
                    return filmLength;
                }

                public void setFilmLength(String filmLength) {
                    this.filmLength = filmLength;
                }

                public String getFilmName() {
                    return filmName;
                }

                public void setFilmName(String filmName) {
                    this.filmName = filmName;
                }

                public String getFilmScore() {
                    return filmScore;
                }

                public void setFilmScore(String filmScore) {
                    this.filmScore = filmScore;
                }

                public int getFilmType() {
                    return filmType;
                }

                public void setFilmType(int filmType) {
                    this.filmType = filmType;
                }

                public String getImgAddress() {
                    return imgAddress;
                }

                public void setImgAddress(String imgAddress) {
                    this.imgAddress = imgAddress;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getShowTime() {
                    return showTime;
                }

                public void setShowTime(String showTime) {
                    this.showTime = showTime;
                }
            }
        }

        public static class BannersBean implements Serializable{
            private static final long serialVersionUID = 3341736575308986126L;
            /**
             * bannerAddress : banner1.png
             * bannerId : 2
             * bannerUrl : www.meetingshop.cn
             */

            private String bannerAddress;
            private String bannerId;
            private String bannerUrl;

            public String getBannerAddress() {
                return bannerAddress;
            }

            public void setBannerAddress(String bannerAddress) {
                this.bannerAddress = bannerAddress;
            }

            public String getBannerId() {
                return bannerId;
            }

            public void setBannerId(String bannerId) {
                this.bannerId = bannerId;
            }

            public String getBannerUrl() {
                return bannerUrl;
            }

            public void setBannerUrl(String bannerUrl) {
                this.bannerUrl = bannerUrl;
            }
        }

        public static class BoxRankingBean implements Serializable{
            private static final long serialVersionUID = -2419378073622162525L;
            /**
             * boxNum : 123421
             * expectNum : 234235
             * filmCats :
             * filmId : 13
             * filmLength :
             * filmName : 素人特工
             * filmScore : 8.0
             * filmType : 0
             * imgAddress : c0bec212d759ad52f22bbb280e551c065000875.jpg
             * score : 8.0
             * showTime : 2018-07-12
             */

            private int boxNum;
            private int expectNum;
            private String filmCats;
            private String filmId;
            private String filmLength;
            private String filmName;
            private String filmScore;
            private int filmType;
            private String imgAddress;
            private String score;
            private String showTime;

            public int getBoxNum() {
                return boxNum;
            }

            public void setBoxNum(int boxNum) {
                this.boxNum = boxNum;
            }

            public int getExpectNum() {
                return expectNum;
            }

            public void setExpectNum(int expectNum) {
                this.expectNum = expectNum;
            }

            public String getFilmCats() {
                return filmCats;
            }

            public void setFilmCats(String filmCats) {
                this.filmCats = filmCats;
            }

            public String getFilmId() {
                return filmId;
            }

            public void setFilmId(String filmId) {
                this.filmId = filmId;
            }

            public String getFilmLength() {
                return filmLength;
            }

            public void setFilmLength(String filmLength) {
                this.filmLength = filmLength;
            }

            public String getFilmName() {
                return filmName;
            }

            public void setFilmName(String filmName) {
                this.filmName = filmName;
            }

            public String getFilmScore() {
                return filmScore;
            }

            public void setFilmScore(String filmScore) {
                this.filmScore = filmScore;
            }

            public int getFilmType() {
                return filmType;
            }

            public void setFilmType(int filmType) {
                this.filmType = filmType;
            }

            public String getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(String imgAddress) {
                this.imgAddress = imgAddress;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }
        }

        public static class ExpectRankingBean implements Serializable{
            private static final long serialVersionUID = 5427678729565555760L;
            /**
             * boxNum : 23453452
             * expectNum : 32233
             * filmCats :
             * filmId : 8
             * filmLength :
             * filmName : 怨灵3
             * filmScore : 7.7
             * filmType : 1
             * imgAddress : d143039b1b72205ffb08b1de6ef38ffe1324616.jpg
             * score : 7.7
             * showTime : 2018-07-19
             */

            private int boxNum;
            private int expectNum;
            private String filmCats;
            private String filmId;
            private String filmLength;
            private String filmName;
            private String filmScore;
            private int filmType;
            private String imgAddress;
            private String score;
            private String showTime;

            public int getBoxNum() {
                return boxNum;
            }

            public void setBoxNum(int boxNum) {
                this.boxNum = boxNum;
            }

            public int getExpectNum() {
                return expectNum;
            }

            public void setExpectNum(int expectNum) {
                this.expectNum = expectNum;
            }

            public String getFilmCats() {
                return filmCats;
            }

            public void setFilmCats(String filmCats) {
                this.filmCats = filmCats;
            }

            public String getFilmId() {
                return filmId;
            }

            public void setFilmId(String filmId) {
                this.filmId = filmId;
            }

            public String getFilmLength() {
                return filmLength;
            }

            public void setFilmLength(String filmLength) {
                this.filmLength = filmLength;
            }

            public String getFilmName() {
                return filmName;
            }

            public void setFilmName(String filmName) {
                this.filmName = filmName;
            }

            public String getFilmScore() {
                return filmScore;
            }

            public void setFilmScore(String filmScore) {
                this.filmScore = filmScore;
            }

            public int getFilmType() {
                return filmType;
            }

            public void setFilmType(int filmType) {
                this.filmType = filmType;
            }

            public String getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(String imgAddress) {
                this.imgAddress = imgAddress;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }
        }

        public static class Top100Bean implements Serializable{
            private static final long serialVersionUID = 6677743976610960467L;
            /**
             * boxNum : 33546234
             * expectNum : 86756434
             * filmCats :
             * filmId : 9
             * filmLength :
             * filmName : 蜘蛛侠英雄远征
             * filmScore : 8.0
             * filmType : 2
             * imgAddress : 5dac476535359b7bb951ff15d37a9d0b153821.jpg
             * score : 8.0
             * showTime : 2018-07-12
             */

            private int boxNum;
            private int expectNum;
            private String filmCats;
            private String filmId;
            private String filmLength;
            private String filmName;
            private String filmScore;
            private int filmType;
            private String imgAddress;
            private String score;
            private String showTime;

            public int getBoxNum() {
                return boxNum;
            }

            public void setBoxNum(int boxNum) {
                this.boxNum = boxNum;
            }

            public int getExpectNum() {
                return expectNum;
            }

            public void setExpectNum(int expectNum) {
                this.expectNum = expectNum;
            }

            public String getFilmCats() {
                return filmCats;
            }

            public void setFilmCats(String filmCats) {
                this.filmCats = filmCats;
            }

            public String getFilmId() {
                return filmId;
            }

            public void setFilmId(String filmId) {
                this.filmId = filmId;
            }

            public String getFilmLength() {
                return filmLength;
            }

            public void setFilmLength(String filmLength) {
                this.filmLength = filmLength;
            }

            public String getFilmName() {
                return filmName;
            }

            public void setFilmName(String filmName) {
                this.filmName = filmName;
            }

            public String getFilmScore() {
                return filmScore;
            }

            public void setFilmScore(String filmScore) {
                this.filmScore = filmScore;
            }

            public int getFilmType() {
                return filmType;
            }

            public void setFilmType(int filmType) {
                this.filmType = filmType;
            }

            public String getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(String imgAddress) {
                this.imgAddress = imgAddress;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }
        }
    }
}
