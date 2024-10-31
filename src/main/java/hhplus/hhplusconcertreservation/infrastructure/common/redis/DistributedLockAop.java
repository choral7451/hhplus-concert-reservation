package hhplus.hhplusconcertreservation.infrastructure.common.redis;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

	private final RedissonClient redissonClient;
	@Around("@annotation(DistributedLock)")
	public Object distributedLock(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock annotation = method.getAnnotation(DistributedLock.class);
		String lockKey = method.getName() + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.key());

		RLock lock = redissonClient.getLock(lockKey);

		try {
			boolean lockable = lock.tryLock(0, annotation.leaseTime(), TimeUnit.MILLISECONDS);
			if (!lockable) {
				log.info("Lock 획득 실패={}", lockKey);
				return false;
			}
			log.info("로직 수행");
			joinPoint.proceed();
		} catch (InterruptedException e) {
			log.info("에러 발생");
			throw e;
		} finally {
			log.info("락 해제");
			lock.unlock();
		}

		return null;
	}

		// try {
		// 	while (true) {
		// 		boolean lockable = lock.tryLock(0, annotation.leaseTime(), TimeUnit.DAYS.MILLISECONDS);
		// 		if (lockable) {
		// 			log.info("락 획득 성공: {}", lockKey);
		// 			break;
		// 		} else {
		// 			log.info("락 획득 실패, 재시도: {}", lockKey);
		// 			Thread.sleep(SPIN_INTERVAL);
		// 		}
		// 	}
		//
		// 	log.info("로직 수행");
		// 	return joinPoint.proceed(); // 원본 메서드 호출
		// } catch (InterruptedException e) {
		// 	log.info("에러 발생");
		// 	throw e;
		// } finally {
		// 	log.info("락 해제");
		// 	lock.unlock();
		// }
}

