package cn.poryoung.htcpmd.common.pojo;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.common.core.web.page.TableSupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageSupport extends TableSupport {
    public static Pageable getPageable() {
        PageDomain pageDomain = getPageDomain();
        return getPageable(pageDomain);
    }

    public static Pageable getPageable(PageDomain pageDomain) {
        if (StrUtil.isNotBlank(pageDomain.getOrderByColumn())) {
            return PageRequest.of(pageDomain.getPageNum(), pageDomain.getPageSize(), Sort.by(Sort.Direction.fromString(pageDomain.getIsAsc()), pageDomain.getOrderByColumn()));
        } else {
            return PageRequest.of(pageDomain.getPageNum(), pageDomain.getPageSize());
        }
    }
}
