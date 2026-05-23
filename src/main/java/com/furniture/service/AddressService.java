package com.furniture.service;

import com.furniture.entity.Address;
import com.furniture.entity.User;
import com.furniture.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserIdOrderByIsDefaultDesc(userId);
    }

    public Address getDefaultAddress(Long userId) {
        return addressRepository.findByUserIdAndIsDefaultTrue(userId).orElse(null);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address saveAddress(Address address, Long userId) {
        // 如果是默认地址，先取消其他地址的默认状态
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            List<Address> addresses = getAddressesByUserId(userId);
            for (Address addr : addresses) {
                addr.setIsDefault(false);
                addressRepository.save(addr);
            }
        }
        User user = new User();
        user.setId(userId);
        address.setUser(user);
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public void setDefaultAddress(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address != null) {
            // 先取消其他地址的默认状态
            List<Address> addresses = getAddressesByUserId(userId);
            for (Address addr : addresses) {
                addr.setIsDefault(false);
                addressRepository.save(addr);
            }
            // 设置当前地址为默认
            address.setIsDefault(true);
            addressRepository.save(address);
        }
    }
}
