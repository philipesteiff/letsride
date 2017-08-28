package com.transportation.letsride.common.ui.adapter

import android.support.v7.widget.RecyclerView

abstract class BaseListAdapter<E>(
    val list: MutableList<E> = mutableListOf()
) : RecyclerView.Adapter<BaseViewHolder<E>>(), MutableList<E> {

  private val lock = Any()

  override fun getItemCount() = size

  override fun add(index: Int, element: E) {
    synchronized(lock) {
      list.add(index, element)
      notifyItemInserted(index)
    }
  }

  override fun add(element: E): Boolean {
    synchronized(lock) {
      with(list.size) {
        if (list.add(element)) {
          notifyItemInserted(this)
          return true
        } else {
          return false
        }
      }
    }
  }

  override fun addAll(index: Int, elements: Collection<E>): Boolean {
    synchronized(lock) {
      if (list.addAll(index, elements)) {
        notifyItemRangeInserted(index, elements.size)
        return true
      } else {
        return false
      }
    }
  }

  override fun addAll(elements: Collection<E>): Boolean {
    synchronized(lock) {
      if (list.addAll(elements)) {
        notifyItemRangeInserted(list.size, elements.size)
        return true
      } else {
        return false
      }
    }
  }

  override fun clear() {
    synchronized(lock, {
      val lastSize = size
      if (lastSize > 0) {
        list.clear()
        notifyItemRangeRemoved(0, lastSize)
      }
    })
  }

  override fun contains(element: E): Boolean = list.contains(element)

  override fun containsAll(elements: Collection<E>): Boolean = list.containsAll(elements)

  override fun get(index: Int): E = list[index]

  override fun indexOf(element: E): Int = list.indexOf(element)

  override fun isEmpty(): Boolean = list.isEmpty()

  override fun iterator(): MutableIterator<E> = list.iterator()

  override fun lastIndexOf(element: E): Int = list.lastIndexOf(element)

  override fun listIterator(): MutableListIterator<E> = list.listIterator()

  override fun listIterator(index: Int): MutableListIterator<E> = list.listIterator(index)

  override fun removeAt(index: Int): E {
    synchronized(lock) {
      return list.removeAt(index).apply { notifyItemRemoved(index) }
    }
  }

  override fun remove(element: E): Boolean {
    synchronized(lock) {
      with(indexOf(element)) {
        if (list.remove(element)) {
          notifyItemRemoved(this)
          return true
        } else {
          return false
        }
      }
    }
  }

  override fun removeAll(elements: Collection<E>): Boolean {
    var modified: Boolean = false
    val iterator = list.iterator()
    while (iterator.hasNext()) {
      val element = iterator.next()
      if (elements.contains(element)) {
        synchronized(lock) {
          val indexOf = indexOf(element)
          iterator.remove()
          notifyItemRemoved(indexOf)
        }
        modified = true
      }
    }
    return modified
  }

  override fun retainAll(elements: Collection<E>): Boolean {
    var modified: Boolean = false

    val iterator = list.iterator()
    while (iterator.hasNext()) {
      val element = iterator.next()
      if (!elements.contains(element)) {
        synchronized(lock) {
          val index = indexOf(element)
          iterator.remove()
          notifyItemRemoved(index)
        }
        modified = true
      }
    }
    return modified
  }

  override fun set(index: Int, element: E): E {
    synchronized(lock, {
      val origin = list.set(index, element)
      notifyItemChanged(index)
      return origin
    })
  }

  override val size: Int
    get() = list.size

  override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
    return list.subList(fromIndex, toIndex)
  }

  override fun equals(other: Any?): Boolean {
    return other is List<*> && list == other
  }

  override fun hashCode(): Int {
    var result = list.hashCode()
    result = 31 * result + lock.hashCode()
    return result
  }

}
