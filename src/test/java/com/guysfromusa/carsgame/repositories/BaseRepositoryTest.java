package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public abstract class BaseRepositoryTest {
}
