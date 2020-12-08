package com.blinkfox.fenix.example.service;

import com.blinkfox.fenix.example.entity.Book;
import com.blinkfox.fenix.example.repository.BookRepository;
import com.blinkfox.fenix.id.IdWorker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图书操作的 Service 类.
 *
 * @author blinkfox on 2020-12-07.
 * @since v2.4.0
 */
@Service
public class BookService {

    private static final IdWorker idWorker = new IdWorker();

    @Resource
    private BookRepository bookRepository;

    /**
     * 使用 saveAll 方法来批量保存图书信息.
     *
     * @param books 图书集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAll(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    /**
     * 使用 saveBatch 方法来测试事务回滚的效果.
     *
     * @param count 数量
     * @param isRollback 是否回滚
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveBatchWithRollback(int count, boolean isRollback) {
        List<Book> books = this.buildBooks(count);
        this.bookRepository.saveBatch(books);
        if (isRollback) {
            throw new RuntimeException("这是我用来测试事务回滚而抛出的异常.");
        }
    }

    /**
     * 使用 saveBatch 方法来批量保存图书信息.
     *
     * @param books 图书集合
     * @param batchSize 批量保存大小.
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveBatch(List<Book> books, Integer batchSize) {
        if (batchSize == null || batchSize <= 0) {
            this.bookRepository.saveBatch(books);
        } else {
            this.bookRepository.saveBatch(books, batchSize);
        }
    }

    /**
     * 使用 updateBatch 方法来批量保存图书信息.
     *
     * @param books 图书集合
     * @param batchSize 批量保存大小.
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBatch(List<Book> books, Integer batchSize) {
        if (batchSize == null || batchSize <= 0) {
            this.bookRepository.updateBatch(books);
        } else {
            this.bookRepository.updateBatch(books, batchSize);
        }
    }

    /**
     * 使用 deleteByIds 方法来批量新增或更新图书信息.
     *
     * @param books ID 集合
     * @param isRollback 是否回滚
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBatchWithRollback(List<Book> books, boolean isRollback) {
        this.bookRepository.updateBatch(books);
        if (isRollback) {
            throw new RuntimeException("这是我用于测试 updateBatch 方法的手动抛出的异常.");
        }
    }

    /**
     * 使用 saveOrUpdateAllByNotNullProperties 方法来批量新增或更新图书信息.
     *
     * @param books 图书集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdateAllByNotNullProperties(List<Book> books) {
        this.bookRepository.saveOrUpdateAllByNotNullProperties(books);
    }

    /**
     * 使用 deleteByIds 方法来批量新增或更新图书信息.
     *
     * @param ids ID 集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByIds(List<String> ids) {
        this.bookRepository.deleteByIds(ids);
    }

    /**
     * 使用 deleteByIds 方法来批量新增或更新图书信息.
     *
     * @param ids ID 集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBatchByIds(List<String> ids, Integer batchSize) {
        if (batchSize == null || batchSize <= 0) {
            this.bookRepository.deleteBatchByIds(ids);
        } else {
            this.bookRepository.deleteBatchByIds(ids, batchSize);
        }
    }

    /**
     * 使用 deleteByIds 方法来批量新增或更新图书信息.
     *
     * @param ids ID 集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBatchByIdsWithRollback(List<String> ids, boolean isRollback) {
        this.bookRepository.deleteBatchByIds(ids);
        if (isRollback) {
            throw new RuntimeException("这是我用来测试事务回滚而抛出的异常.");
        }
    }

    /**
     * 构建指定条数的图书信息集合.
     *
     * @param count 条数
     * @return 图书信息集合
     */
    public List<Book> buildBooks(int count) {
        final Date now = new Date();
        final String publishAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        List<Book> books = new ArrayList<>(count);
        for (int i = 1; i <= count; ++i) {
            books.add(new Book()
                    .setId(idWorker.get62RadixId())
                    .setName("图书名称" + i)
                    .setIsbn("book-isbn-" + i)
                    .setAuthor("作者" + i)
                    .setSummary("这是图书的描述信息" + i)
                    .setTotalPage(new Random().nextInt(1000) + 200)
                    .setPublishingHouse("第" + i + "人民出版社")
                    .setPublishAt(publishAt)
                    .setCreateTime(now)
                    .setUpdateTime(new Date()));
        }
        return books;
    }

    /**
     * 构建指定条数的图书信息集合.
     *
     * @param books 待修改的图书
     * @return 图书信息集合
     */
    public List<Book> buildUpdateBooks(List<Book> books) {
        final Date now = new Date();
        final String publishAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        for (int i = 1, len = books.size(); i <= len; ++i) {
            books.get(i - 1)
                    .setName("[改]图书名称" + i)
                    .setIsbn("[改]book-isbn-" + i)
                    .setAuthor("[改]作者" + i)
                    .setSummary("[改]这是图书的描述信息" + i)
                    .setTotalPage(new Random().nextInt(1000) + 200)
                    .setPublishingHouse("[改]第" + i + "人民出版社")
                    .setPublishAt(publishAt)
                    .setCreateTime(now)
                    .setUpdateTime(new Date());
        }
        return books;
    }

    /**
     * 构建指定条数的图书信息集合.
     *
     * @param books 待修改的图书
     * @return 图书信息集合
     */
    public List<Book> buildUpdateNotNullProperties(List<Book> books) {
        final Date now = new Date();
        for (int i = 1, len = books.size(); i <= len; ++i) {
            books.get(i - 1)
                    .setName("[改2]图书名称" + i)
                    .setIsbn("[改2]book-isbn-" + i)
                    .setAuthor(null)
                    .setSummary(null)
                    .setTotalPage(new Random().nextInt(1000) + 200)
                    .setPublishingHouse(null)
                    .setPublishAt(null)
                    .setCreateTime(null)
                    .setUpdateTime(now);
        }
        return books;
    }

    /**
     * 构建指定条数的图书信息集合.
     *
     * @param books 待修改的图书
     * @return 图书信息集合
     */
    public List<String> getBookIds(List<Book> books) {
        List<String> ids = new ArrayList<>(books.size());
        for (Book book : books) {
            ids.add(book.getId());
        }
        return ids;
    }

}
