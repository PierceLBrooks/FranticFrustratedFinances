
// Author: Pierce Brooks
// Reference: https://github.com/frohoff/jdk8u-dev-jdk/blob/master/src/share/classes/java/lang/invoke/MutableCallSite.java

package com.piercelbrooks.hax;

import java.lang.invoke.MutableCallSite;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Hax
{
    private static WeakHashMap<Class<?>, Type> types = null;

    public static Type getType(Class<?> type)
    {
        if (types == null)
        {
            types = new WeakHashMap<>();
        }
        if (!types.containsKey(type))
        {
            types.put(type, new Type(type));
        }
        return types.get(type);
    }

    public static class Type
    {
        public ClassValue.ClassValueMap classValueMap;

        private Class<?> type;

        public Type(Class<?> type)
        {
            this.type = type;
        }

        public Class<?> getType()
        {
            return type;
        }
    }


    /**
     * Performs a synchronization operation on each call site in the given array,
     * forcing all other threads to throw away any cached values previously
     * loaded from the target of any of the call sites.
     * <p>
     * This operation does not reverse any calls that have already started
     * on an old target value.
     * (Java supports {@linkplain java.lang.Object#wait() forward time travel} only.)
     * <p>
     * The overall effect is to force all future readers of each call site's target
     * to accept the most recently stored value.
     * ("Most recently" is reckoned relative to the {@code syncAll} itself.)
     * Conversely, the {@code syncAll} call may block until all readers have
     * (somehow) decached all previous versions of each call site's target.
     * <p>
     * To avoid race conditions, calls to {@code setTarget} and {@code syncAll}
     * should generally be performed under some sort of mutual exclusion.
     * Note that reader threads may observe an updated target as early
     * as the {@code setTarget} call that install the value
     * (and before the {@code syncAll} that confirms the value).
     * On the other hand, reader threads may observe previous versions of
     * the target until the {@code syncAll} call returns
     * (and after the {@code setTarget} that attempts to convey the updated version).
     * <p>
     * This operation is likely to be expensive and should be used sparingly.
     * If possible, it should be buffered for batch processing on sets of call sites.
     * <p>
     * If {@code sites} contains a null element,
     * a {@code NullPointerException} will be raised.
     * In this case, some non-null elements in the array may be
     * processed before the method returns abnormally.
     * Which elements these are (if any) is implementation-dependent.
     *
     * <h1>Java Memory Model details</h1>
     * In terms of the Java Memory Model, this operation performs a synchronization
     * action which is comparable in effect to the writing of a volatile variable
     * by the current thread, and an eventual volatile read by every other thread
     * that may access one of the affected call sites.
     * <p>
     * The following effects are apparent, for each individual call site {@code S}:
     * <ul>
     * <li>A new volatile variable {@code V} is created, and written by the current thread.
     *     As defined by the JMM, this write is a global synchronization event.
     * <li>As is normal with thread-local ordering of write events,
     *     every action already performed by the current thread is
     *     taken to happen before the volatile write to {@code V}.
     *     (In some implementations, this means that the current thread
     *     performs a global release operation.)
     * <li>Specifically, the write to the current target of {@code S} is
     *     taken to happen before the volatile write to {@code V}.
     * <li>The volatile write to {@code V} is placed
     *     (in an implementation specific manner)
     *     in the global synchronization order.
     * <li>Consider an arbitrary thread {@code T} (other than the current thread).
     *     If {@code T} executes a synchronization action {@code A}
     *     after the volatile write to {@code V} (in the global synchronization order),
     *     it is therefore required to see either the current target
     *     of {@code S}, or a later write to that target,
     *     if it executes a read on the target of {@code S}.
     *     (This constraint is called "synchronization-order consistency".)
     * <li>The JMM specifically allows optimizing compilers to elide
     *     reads or writes of variables that are known to be useless.
     *     Such elided reads and writes have no effect on the happens-before
     *     relation.  Regardless of this fact, the volatile {@code V}
     *     will not be elided, even though its written value is
     *     indeterminate and its read value is not used.
     * </ul>
     * Because of the last point, the implementation behaves as if a
     * volatile read of {@code V} were performed by {@code T}
     * immediately after its action {@code A}.  In the local ordering
     * of actions in {@code T}, this read happens before any future
     * read of the target of {@code S}.  It is as if the
     * implementation arbitrarily picked a read of {@code S}'s target
     * by {@code T}, and forced a read of {@code V} to precede it,
     * thereby ensuring communication of the new target value.
     * <p>
     * As long as the constraints of the Java Memory Model are obeyed,
     * implementations may delay the completion of a {@code syncAll}
     * operation while other threads ({@code T} above) continue to
     * use previous values of {@code S}'s target.
     * However, implementations are (as always) encouraged to avoid
     * livelock, and to eventually require all threads to take account
     * of the updated target.
     *
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * For performance reasons, {@code syncAll} is not a virtual method
     * on a single call site, but rather applies to a set of call sites.
     * Some implementations may incur a large fixed overhead cost
     * for processing one or more synchronization operations,
     * but a small incremental cost for each additional call site.
     * In any case, this operation is likely to be costly, since
     * other threads may have to be somehow interrupted
     * in order to make them notice the updated target value.
     * However, it may be observed that a single call to synchronize
     * several sites has the same formal effect as many calls,
     * each on just one of the sites.
     *
     * <p style="font-size:smaller;">
     * <em>Implementation Note:</em>
     * Simple implementations of {@code MutableCallSite} may use
     * a volatile variable for the target of a mutable call site.
     * In such an implementation, the {@code syncAll} method can be a no-op,
     * and yet it will conform to the JMM behavior documented above.
     *
     * @param sites an array of call sites to be synchronized
     * @throws NullPointerException if the {@code sites} array reference is null
     *                              or the array contains a null
     */
    public static void syncAll(MutableCallSite[] sites) {
        if (sites.length == 0)  return;
        STORE_BARRIER.lazySet(0);
        for (int i = 0; i < sites.length; i++) {
            sites[i].getClass();  // trigger NPE on first null
        }
        // FIXME: NYI
    }
    private static final AtomicInteger STORE_BARRIER = new AtomicInteger();
}
