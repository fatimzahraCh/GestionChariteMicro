package com.ecom.participationservice.feignClient;

import com.ecom.participationservice.exception.ServiceExterneIndisponibleException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class ResilientRequests {

    private static final String USER_SERVICE = "user-service";
    private static final String ACTION_SERVICE = "actioncharite-service";

    private final UserRequest userRequest;
    private final ActionRequest actionRequest;

    public ResilientRequests(UserRequest userRequest, ActionRequest actionRequest) {
        this.userRequest = userRequest;
        this.actionRequest = actionRequest;
    }

    // Lecture enrichie : repli tolérant avec champs d'enrichissement à null si indisponible
    @Retry(name = USER_SERVICE, fallbackMethod = "userRepliLecture")
    @CircuitBreaker(name = USER_SERVICE)
    public UserDto getUserByIdLecture(Long id) {
        return userRequest.getUserById(id);
    }

    // Lecture enrichie : repli tolérant avec champs d'enrichissement à null si indisponible
    @Retry(name = ACTION_SERVICE, fallbackMethod = "actionRepliLecture")
    @CircuitBreaker(name = ACTION_SERVICE)
    public ActionDto getActionByIdLecture(Long id) {
        return actionRequest.getActionById(id);
    }

    // Création (createParticipation) : repli strict qui lève HTTP 503 si user-service est indisponible
    @Retry(name = USER_SERVICE, fallbackMethod = "userRepliCreation")
    @CircuitBreaker(name = USER_SERVICE)
    public UserDto getUserByIdCreation(Long id) {
        return userRequest.getUserById(id);
    }

    // Création (createParticipation) : repli strict qui lève HTTP 503 si actioncharite-service est indisponible
    @Retry(name = ACTION_SERVICE, fallbackMethod = "actionRepliCreation")
    @CircuitBreaker(name = ACTION_SERVICE)
    public ActionDto getActionByIdCreation(Long id) {
        return actionRequest.getActionById(id);
    }

    // Fallback tolérant en lecture : conserve l'id et laisse les autres champs à null
    private UserDto userRepliLecture(Long id, RuntimeException ex) {
        if (!estIndisponible(ex)) {
            throw ex;
        }
        UserDto fallback = new UserDto();
        fallback.setId(id);
        return fallback;
    }

    // Fallback tolérant en lecture : conserve l'id et laisse les autres champs à null
    private ActionDto actionRepliLecture(Long id, RuntimeException ex) {
        if (!estIndisponible(ex)) {
            throw ex;
        }
        ActionDto fallback = new ActionDto();
        fallback.setId(id);
        return fallback;
    }

    // Fallback strict en création : traduit l'indisponibilité en ServiceExterneIndisponibleException (503)
    private UserDto userRepliCreation(Long id, RuntimeException ex) {
        if (!estIndisponible(ex)) {
            throw ex;
        }
        throw new ServiceExterneIndisponibleException(
                "user-service est momentanément indisponible. Réessayez plus tard.", ex);
    }

    // Fallback strict en création : traduit l'indisponibilité en ServiceExterneIndisponibleException (503)
    private ActionDto actionRepliCreation(Long id, RuntimeException ex) {
        if (!estIndisponible(ex)) {
            throw ex;
        }
        throw new ServiceExterneIndisponibleException(
                "actioncharite-service est momentanément indisponible. Réessayez plus tard.", ex);
    }

    // Détecte un circuit ouvert ou une erreur de transport / 503 ; laisse passer le reste (ex. 404)
    private boolean estIndisponible(RuntimeException ex) {
        return ex instanceof CallNotPermittedException
                || (ex instanceof FeignException feign && (feign.status() == -1 || feign.status() == 503));
    }
}
