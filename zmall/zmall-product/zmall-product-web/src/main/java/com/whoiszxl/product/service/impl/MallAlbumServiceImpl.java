package com.whoiszxl.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.product.mapper.MallAlbumMapper;
import com.whoiszxl.product.entity.MallAlbum;
import com.whoiszxl.product.service.MallAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Slf4j
@Service
@Transactional
public class MallAlbumServiceImpl extends ServiceImpl<MallAlbumMapper, MallAlbum> implements MallAlbumService {

}
