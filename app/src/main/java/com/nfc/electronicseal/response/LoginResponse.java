package com.nfc.electronicseal.response;

import com.nfc.electronicseal.node.CustomerPhoneNode;
import com.nfc.electronicseal.node.UserNode;

import java.util.List;

public class LoginResponse extends Response<UserNode>{
    private List<CustomerPhoneNode> customerPhoneList;

    public List<CustomerPhoneNode> getCustomerPhoneList() {
        return customerPhoneList;
    }

    public void setCustomerPhoneList(List<CustomerPhoneNode> customerPhoneList) {
        this.customerPhoneList = customerPhoneList;
    }
}
