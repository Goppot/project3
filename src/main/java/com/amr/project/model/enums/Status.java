package com.amr.project.model.enums;

public enum Status {

    START {
        @Override
        public String statusDescription() {
            return "Покупатель разместил заказ";
        }
    },

    CANCELLED {
        @Override
        public String statusDescription() {
            return "Заказ был отменен";
        }
    },

    COMPLETE {
        @Override
        public String statusDescription() {
            return "Заказ выполнен";
        }
    },

    PACKAGED {
        @Override
        public String statusDescription() {
            return "Заказ готов к отправке";
        }
    },

    SENT {
        @Override
        public String statusDescription() {
            return "Заказ отправлен";
        }
    },

    DELIVERED {
        @Override
        public String statusDescription() {
            return "Заказ доставлен покупателю";
        }
    },

    RETURNED {
        @Override
        public String statusDescription() {
            return "Возврат товара осуществлен";
        }
    },

    PAID {
        @Override
        public String statusDescription() {
            return "Покупатель оплатил заказ";
        }
    },

    REFUNDED {
        @Override
        public String statusDescription() {
            return "Возврат средств произведен";
        }
    },

    RETURNING {
        @Override
        public String statusDescription() {
            return "Покупатель отправил заявку на возврат";
        }
    };

    public abstract String statusDescription();
}
