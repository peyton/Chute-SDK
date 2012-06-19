// Copyright (c) 2011, Chute Corporation. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification,
// are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above copyright notice, this
// list of conditions and the following disclaimer.
// * Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// * Neither the name of the Chute Corporation nor the names
// of its contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
// IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
// LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
// OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
// OF THE POSSIBILITY OF SUCH DAMAGE.
//
package com.darko.imagedownloader.queue;

import java.util.EmptyStackException;
import java.util.Vector;

/**
 * @author darko.grozdanovski
 * @param <E>
 */
public class QueueStrategy<E> implements DequeStrategy<E> {

	final Vector<E> queue = new Vector<E>();

	@Override
	public E get(int index) {
		return queue.get(index);
	}

	@Override
	public void remove(int index) {
		queue.remove(index);
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void push(E element) {
		queue.addElement(element);
	}

	@Override
	public E pop() {
		E obj;
		obj = peek();
		queue.removeElementAt(0);
		return obj;
	}

	public synchronized E peek() {
		int len = size();
		if (len == 0) {
			throw new EmptyStackException();
		}
		return queue.elementAt(0);
	}
}