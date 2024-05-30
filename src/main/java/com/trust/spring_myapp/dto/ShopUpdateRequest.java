package com.trust.spring_myapp.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ショップ情報更新リクエストデータ
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ShopUpdateRequest extends ShopRequest implements Serializable {

  /**
   * ショップID
   */
  @NotNull
  private Long id;
}