package au.id.tmm.collections

import au.id.tmm.collections.classes.SafeGroupBy

package object syntax
    extends AnyRef
    with SafeGroupBy.ToSafeGroupByOps
    with IterableOps.ToIterableOps
    with IteratorOps.ToIteratorOps
    with SeqOps.ToSeqOps
    with BufferedIteratorOps.ToBufferedIteratorOps
