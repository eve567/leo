package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.leo.domain.models.Test;
import net.ufrog.leo.domain.repositories.TestRepository;
import net.ufrog.leo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-09
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    @Transactional
    public void testParallelTransactional() {
        Logger.info(">>>>>>>>>>>>>>>>> start parallel.");
        IntStream.range(0, 100).parallel().forEach(idx -> {
            Logger.info("%03d - %s", idx, Thread.currentThread().getName());
            if (idx == 78) throw new RuntimeException();

            Test test = new Test();
            test.setName("test_" + idx);
            testRepository.save(test);
        });
        Logger.info(">>>>>>>>>>>>>>>>> finish parallel.");
    }

    @Override
    @Transactional
    public void testUtf8Len() {
        Test test = new Test();
        test.setName("一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四");
        testRepository.save(test);

        test = new Test();
        test.setName("一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五六七八九零一二三四五");
        testRepository.save(test);
    }
}
