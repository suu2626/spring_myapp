package com.trust.spring_myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trust.spring_myapp.dto.ShopRequest;
import com.trust.spring_myapp.dto.ShopUpdateRequest;
import com.trust.spring_myapp.entity.Shop;
import com.trust.spring_myapp.entity.User;
import com.trust.spring_myapp.repository.ShopRepository;

/**
 * ショップ情報 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopService {

	/**
	 * ショップ情報 Repository
	 */
	@Autowired
	ShopRepository shopRepository;
	
	public List<Shop> findAll() {
        return shopRepository.findAll();
    }
	
	public List<Shop> findByUser(User user) {
        return shopRepository.findByUser(user);
    }
	
	public void createShop(Shop shop) {
        shopRepository.save(shop);
    }
	
	public void updateShop(Shop shop) {
        shopRepository.save(shop);
    }

    public void deleteShop(Long id) {
        shopRepository.deleteById(id);
    }

    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }
	
	/**
	 * ショップ情報 全検索
	 * @return 検索結果
	 */
	public List<Shop> searchAll() {
		return shopRepository.findAll();
	}

	/**
	   * ショップ情報 新規登録
	   * @param shop ショップ情報
	   */
	public void createShop(ShopRequest shopRequest, User user) {
        Shop shop = new Shop();
        shop.setName(shopRequest.getName());
        shop.setDescription(shopRequest.getDescription());
        shop.setUser(user);
        shopRepository.save(shop);
    }

	/**
	 * ショップ情報 主キー検索
	 * @return 検索結果
	 */
	public Shop findById(Long id) {
		return shopRepository.findById(id).orElse(null);
	}

	/**
	   * ショップ情報 更新
	   * @param shop ショップ情報
	   */
	public void update(ShopUpdateRequest shopUpdateRequest) {
        Shop shop = shopRepository.findById(shopUpdateRequest.getId()).orElse(null);
        if (shop != null) {
            shop.setName(shopUpdateRequest.getName());
            shop.setDescription(shopUpdateRequest.getDescription());
            shopRepository.save(shop);
        }
    }

	/**
	 * ショップ情報 物理削除
	 * @param id ショップID
	 */
	public void delete(Long id) {
		Shop shop = findById(id);
		shopRepository.delete(shop);
	}
}