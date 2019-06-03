package com.shenkangyun.asthmaproject.BeanFolder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntity implements Parcelable {


    /**
     * symptomArray : [{"sectionName":"哮喘症状","items":[{"id":623,"selectString":"1,1,0,0,0,0,0","itemName":"咳嗽","type":"checkBox"},{"id":624,"selectString":"0,0,1,0,0,0,0","itemName":"喘息","type":"checkBox"},{"id":625,"selectString":"0,0,1,0,0,0,0","itemName":"气急","type":"checkBox"},{"id":626,"selectString":"1,1,0,0,0,0,0","itemName":"胸闷","type":"checkBox"},{"id":627,"selectString":"0,1,0,0,0,0,0","itemName":"夜间因哮喘憋醒","type":"checkBox"},{"id":628,"selectString":"1,0,0,0,0,0,0","itemName":"哮喘使日常活动受到影响","type":"checkBox"}]}]
     * addMedicineArray : [{"sectionName":"因症状加重加吸的药物","items":[{"id":629,"selectString":"T","itemName":"药物名","type":"text"},{"id":630,"selectString":"3,,,,,,","itemName":"使用次数","type":"checkBoxText"}]}]
     * useMedicineArray : [{"sectionName":"每天早晚按时使用的药物","items":[{"id":631,"selectString":"T","itemName":"药物名","type":"text"},{"id":632,"selectString":"1,,,,,,","itemName":"每次用量","type":"checkBoxText"},{"id":633,"selectString":"3,,,,,,","itemName":"每日使用次数","type":"checkBoxText"}]},{"sectionName":"每天早晚按时使用的药物","items":[{"id":634,"selectString":"5","itemName":"药物名","type":"text"},{"id":635,"selectString":"5,,,,,,","itemName":"每次用量","type":"checkBoxText"},{"id":636,"selectString":"5,,,,,,","itemName":"每日使用次数","type":"checkBoxText"}]}]
     * week : 1
     */

    private String week;
    /**
     * sectionName : 哮喘症状
     * items : [{"id":623,"selectString":"1,1,0,0,0,0,0","itemName":"咳嗽","type":"checkBox"},{"id":624,"selectString":"0,0,1,0,0,0,0","itemName":"喘息","type":"checkBox"},{"id":625,"selectString":"0,0,1,0,0,0,0","itemName":"气急","type":"checkBox"},{"id":626,"selectString":"1,1,0,0,0,0,0","itemName":"胸闷","type":"checkBox"},{"id":627,"selectString":"0,1,0,0,0,0,0","itemName":"夜间因哮喘憋醒","type":"checkBox"},{"id":628,"selectString":"1,0,0,0,0,0,0","itemName":"哮喘使日常活动受到影响","type":"checkBox"}]
     */

    private List<SymptomArrayEntity> symptomArray;
    /**
     * sectionName : 因症状加重加吸的药物
     * items : [{"id":629,"selectString":"T","itemName":"药物名","type":"text"},{"id":630,"selectString":"3,,,,,,","itemName":"使用次数","type":"checkBoxText"}]
     */

    private List<AddMedicineArrayEntity> addMedicineArray;
    /**
     * sectionName : 每天早晚按时使用的药物
     * items : [{"id":631,"selectString":"T","itemName":"药物名","type":"text"},{"id":632,"selectString":"1,,,,,,","itemName":"每次用量","type":"checkBoxText"},{"id":633,"selectString":"3,,,,,,","itemName":"每日使用次数","type":"checkBoxText"}]
     */

    private List<UseMedicineArrayEntity> useMedicineArray;

    public void setWeek(String week) {
        this.week = week;
    }

    public void setSymptomArray(List<SymptomArrayEntity> symptomArray) {
        this.symptomArray = symptomArray;
    }

    public void setAddMedicineArray(List<AddMedicineArrayEntity> addMedicineArray) {
        this.addMedicineArray = addMedicineArray;
    }

    public void setUseMedicineArray(List<UseMedicineArrayEntity> useMedicineArray) {
        this.useMedicineArray = useMedicineArray;
    }

    public String getWeek() {
        return week;
    }

    public List<SymptomArrayEntity> getSymptomArray() {
        return symptomArray;
    }

    public List<AddMedicineArrayEntity> getAddMedicineArray() {
        return addMedicineArray;
    }

    public List<UseMedicineArrayEntity> getUseMedicineArray() {
        return useMedicineArray;
    }

    public static class SymptomArrayEntity {
        private String sectionName;
        /**
         * id : 623
         * selectString : 1,1,0,0,0,0,0
         * itemName : 咳嗽
         * type : checkBox
         */

        private List<ItemsEntity> items;

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public String getSectionName() {
            return sectionName;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class ItemsEntity {
            private int id;
            private String selectString;
            private String itemName;
            private String type;

            public void setId(int id) {
                this.id = id;
            }

            public void setSelectString(String selectString) {
                this.selectString = selectString;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public String getSelectString() {
                return selectString;
            }

            public String getItemName() {
                return itemName;
            }

            public String getType() {
                return type;
            }
        }
    }

    public static class AddMedicineArrayEntity {
        private String sectionName;
        /**
         * id : 629
         * selectString : T
         * itemName : 药物名
         * type : text
         */

        private List<ItemsEntity> items;

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public String getSectionName() {
            return sectionName;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class ItemsEntity {
            private int id;
            private String selectString;
            private String itemName;
            private String type;

            public void setId(int id) {
                this.id = id;
            }

            public void setSelectString(String selectString) {
                this.selectString = selectString;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public String getSelectString() {
                return selectString;
            }

            public String getItemName() {
                return itemName;
            }

            public String getType() {
                return type;
            }
        }
    }

    public static class UseMedicineArrayEntity {
        private String sectionName;
        /**
         * id : 631
         * selectString : T
         * itemName : 药物名
         * type : text
         */

        private List<ItemsEntity> items;

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public String getSectionName() {
            return sectionName;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class ItemsEntity {
            private int id;
            private String selectString;
            private String itemName;
            private String type;

            public void setId(int id) {
                this.id = id;
            }

            public void setSelectString(String selectString) {
                this.selectString = selectString;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public String getSelectString() {
                return selectString;
            }

            public String getItemName() {
                return itemName;
            }

            public String getType() {
                return type;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.week);
        dest.writeList(this.symptomArray);
        dest.writeList(this.addMedicineArray);
        dest.writeList(this.useMedicineArray);
    }

    public DiaryEntity() {
    }

    protected DiaryEntity(Parcel in) {
        this.week = in.readString();
        this.symptomArray = new ArrayList<SymptomArrayEntity>();
        in.readList(this.symptomArray, SymptomArrayEntity.class.getClassLoader());
        this.addMedicineArray = new ArrayList<AddMedicineArrayEntity>();
        in.readList(this.addMedicineArray, AddMedicineArrayEntity.class.getClassLoader());
        this.useMedicineArray = new ArrayList<UseMedicineArrayEntity>();
        in.readList(this.useMedicineArray, UseMedicineArrayEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<DiaryEntity> CREATOR = new Parcelable.Creator<DiaryEntity>() {
        @Override
        public DiaryEntity createFromParcel(Parcel source) {
            return new DiaryEntity(source);
        }

        @Override
        public DiaryEntity[] newArray(int size) {
            return new DiaryEntity[size];
        }
    };
}
